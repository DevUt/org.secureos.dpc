package org.secureos.dpc.packageManagement

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

    fun returnData(): MutableList<ApplicationInfo> {
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
            runBlocking {
                if (PackagePrefManager(context.applicationContext).readEnabled(app.packageName) == 0) {
                    var enableStatus = 1
                    if (!app.enabled)
                        enableStatus = 2
                    PackagePrefManager(context.applicationContext).writeEnabled(
                        app.packageName,
                        enableStatus
                    )
                }
            }
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
            packageObj.writeEnabled("com.android.documentsui", 2)
            packageObj.writeEnabled("com.android.messaging", 2)
            packageObj.writeEnabled("com.android.dialer", 2)
            packageObj.writeEnabled("com.android.server.telecom", 2)
            packageObj.writeEnabled("com.android.phone", 2)
            packageObj.writeEnabled("com.android.talkback", 2)
            packageObj.writeEnabled("com.android.cellbroadcastreceiver.module", 2)
            packageObj.writeEnabled("com.android.cellbroadcastservice", 2)
            packageObj.writeEnabled("com.android.providers.telephony", 1) // settings this will break SystemUI
            packageObj.writeEnabled("com.android.captiveportallogin", 1) // setting this will break SystemUI
        }
    }

    fun Boolean.toInt() = if (this) 1 else 0
}