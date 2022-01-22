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
    private val cn = ComponentName(this,DeviceAdmin::class.java)
    private val dpm = getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_policy_enforcer)
        if(!dpm.isAdminActive(cn)){
            Snackbar.make(findViewById(android.R.id.content),"Not a Device Admin",Snackbar.LENGTH_SHORT).show()
            finishActivity(-1)
        }
        Snackbar.make(findViewById(android.R.id.content),"Enforcing Package Policy",Snackbar.LENGTH_SHORT).show()
        enforcePackagePolicy()
        Snackbar.make(findViewById(android.R.id.content),"Enforcing Password Policy",Snackbar.LENGTH_SHORT).show()
        enforcePasswordPolicy()
    }
    private fun enforcePackagePolicy(){
        val packageList =
            packageManager.getInstalledApplications(PackageManager.MATCH_DISABLED_COMPONENTS or PackageManager.MATCH_DISABLED_COMPONENTS)
        val packageRead = PackagePrefManager(this)

        for(app in packageList){
            runBlocking {
                if(packageRead.readEnabled(app.packageName) == 2){
                    dpm.setApplicationHidden(cn,app.packageName,true)
                }
            }
        }
    }
    private fun enforcePasswordPolicy(){
//        dpm.requiredPasswordComplexity = DevicePolicyManager.PASSWORD_COMPLEXITY_MEDIUM
        dpm.setMaximumFailedPasswordsForWipe(cn,10)
    }

}