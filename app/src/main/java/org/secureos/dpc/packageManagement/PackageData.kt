package org.secureos.dpc.packageManagement

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager

class PackageData(val context : Context,extraSpecification : Int,val includeSystemApps : Boolean){
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
        return packages
    }

}