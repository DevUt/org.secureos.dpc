package com.utkarsh.firsttestdpm.policyEnforcer

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.utkarsh.firsttestdpm.R
import com.utkarsh.firsttestdpm.deviceAdmin.DeviceAdmin
import com.utkarsh.firsttestdpm.packageManagement.PackagePrefManager
import kotlinx.coroutines.runBlocking

class PolicyEnforcer : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_policy_enforcer)
        val cn = ComponentName(this,DeviceAdmin::class.java)
        val dpm = getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
        if(!dpm.isAdminActive(cn)){
            Snackbar.make(findViewById(android.R.id.content),"Not a Device Admin",Snackbar.LENGTH_SHORT)
            finishActivity(-1)
        }
        Snackbar.make(findViewById(android.R.id.content),"Enforcing Package Policy",Snackbar.LENGTH_SHORT)
        enforcePackagePolicy()
    }
    private fun enforcePackagePolicy(){
        val packageList =
            packageManager.getInstalledApplications(PackageManager.MATCH_DISABLED_COMPONENTS or PackageManager.MATCH_DISABLED_COMPONENTS)
        val packageRead = PackagePrefManager(this)

        for(app in packageList){
            val cn = ComponentName(this,DeviceAdmin::class.java)
            val dpm = getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
            runBlocking {
                if(packageRead.readEnabled(app.packageName) == 2){
                    dpm.setApplicationHidden(cn,app.packageName,true)
                }
            }
        }
    }
}