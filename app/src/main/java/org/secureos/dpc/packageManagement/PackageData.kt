package org.secureos.dpc.packageManagement

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import kotlinx.coroutines.runBlocking

class PackageData(val context : Context, extraSpecification : Int, private val includeSystemApps : Boolean){
    private val packages = mutableListOf<ApplicationInfo>()
    private val packageList : List<ApplicationInfo> = context.packageManager.getInstalledApplications(extraSpecification)

    fun returnData() : MutableList<ApplicationInfo>{
        for(app in packageList){
            val systemPackage = "android"
            if(!includeSystemApps){
                if(context.packageManager.checkSignatures(systemPackage,app.packageName) == PackageManager.SIGNATURE_MATCH){
                    continue
                }
            }
            packages.add(app)
        }
        packages.sortBy { it.loadLabel(context.packageManager).toString() }
        return packages
    }
    fun enforceDefaults(){
        runBlocking {
            val packageObj = PackagePrefManager(context.applicationContext)
            packageObj.writeEnabled("com.android.dialer.binary.aosp.AospDialerApplication",2)
            packageObj.writeEnabled("com.android.messaging.BugleApplication",2)
        }
    }
}