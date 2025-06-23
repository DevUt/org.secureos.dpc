package org.sos.asp.packageManagement

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import kotlinx.coroutines.runBlocking

class PackageData(
    val context: Context,
    extraSpecification: Int,
    private val includeSystemApps: Boolean
) {
    private val packages = mutableListOf<ApplicationInfo>()
    private val packageList: List<ApplicationInfo> =
        context.packageManager.getInstalledApplications(extraSpecification)

    suspend fun returnData(includeBannableApps: Boolean = false): MutableList<ApplicationInfo> {
        for (app in packageList) {
            val systemPackage = "android"
            if (!includeSystemApps) {
                if (context.packageManager.checkSignatures(
                        systemPackage,
                        app.packageName
                    ) == PackageManager.SIGNATURE_MATCH
                ) {
                    continue
                }
            }
            if (PackagePrefManager(context.applicationContext).readEnabled(app.packageName) == 0) {
                var enableStatus = 1
                if (!app.enabled)
                    enableStatus = 2
                PackagePrefManager(context.applicationContext).writeEnabled(
                    app.packageName,
                    enableStatus
                )
            }
            if (!includeBannableApps && unBannableList().contains(app.packageName))
                continue
            packages.add(app)
        }
        packages.sortBy { it.loadLabel(context.packageManager).toString() }
        return packages
    }

    fun enforceDefaults() {
        runBlocking {
            val packageObj = PackagePrefManager(context.applicationContext)
            packageObj.writeEnabled("com.android.dialer.binary.aosp.AospDialerApplication", 2)
            packageObj.writeEnabled("com.android.messaging.BugleApplication", 2)
            packageObj.writeEnabled("com.android.egg", 2)
            packageObj.writeEnabled("com.android.documentsui", 1)
            packageObj.writeEnabled("com.android.messaging", 2)
            packageObj.writeEnabled("com.android.dialer", 2)
            packageObj.writeEnabled("com.android.server.telecom", 2)
            packageObj.writeEnabled("com.android.phone", 1)
            packageObj.writeEnabled("com.android.talkback", 2)
            packageObj.writeEnabled("com.android.cellbroadcastreceiver.module", 2)
            packageObj.writeEnabled("com.android.cellbroadcastservice", 2)
            packageObj.writeEnabled("com.android.server.devicelock.DeviceLockService", 1)
            packageObj.writeEnabled("com.android.devicelockcontroller",1)
            packageObj.writeEnabled(
                "com.android.providers.telephony",
                1
            ) // settings this will break SystemUI
            packageObj.writeEnabled(
                "com.android.captiveportallogin",
                1
            ) // setting this will break SystemUI
        }
    }

    fun unBannableList(): List<String> {
        return listOf(
            "com.android.captiveportallogin",
            "com.android.providers.telephony",
            "org.sos.asp",
            "com.android.phone",
            "com.android.server.devicelock.DeviceLockService",
            "com.android.devicelockcontroller"
        )
    }

    fun Boolean.toInt() = if (this) 1 else 0
}