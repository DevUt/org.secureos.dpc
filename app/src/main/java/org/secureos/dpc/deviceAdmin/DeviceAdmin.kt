package org.secureos.dpc.deviceAdmin

import android.app.admin.DeviceAdminReceiver
import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.runBlocking
import org.secureos.dpc.misc.Misc
import org.secureos.dpc.misc.MiscPrefManager
import org.secureos.dpc.packageManagement.PackageData
import org.secureos.dpc.packageManagement.PackagePrefManager
import org.secureos.dpc.permissionManagement.PermissionData
import org.secureos.dpc.permissionManagement.PermissionPrefManager

class DeviceAdmin : DeviceAdminReceiver(){
    companion object{
        const val TAG = "deviceAdmin"
    }
    lateinit var  dpm : DevicePolicyManager
    lateinit var  cn : ComponentName
    override fun onEnabled(context: Context, intent: Intent) {
        super.onEnabled(context, intent)
        Log.d(TAG,"enabled")
        Toast.makeText(context,"DeviceAdmin enabled",Toast.LENGTH_SHORT).show()
        dpm = context.getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
        cn = ComponentName(context.applicationContext,DeviceAdmin::class.java)
        Log.d(TAG,"Starting to enforce Policy")
        Log.d(TAG,"Starting to enforce Password policy")
        enablePasswordPolicy(context.applicationContext)
        Log.d(TAG,"Success in setting password policy")
        Log.d(TAG,"Starting to enforce Package policy")
        enablePackagePolicy(context.applicationContext)
        Log.d(TAG,"Enforced package policy")
        Log.d(TAG,"Starting to enforce Permission policy")
        // Wait on permission Policy
        enablePermissionPolicy(context.applicationContext)
        Log.d(TAG,"Enforced permission policy")
        Log.d(TAG,"Starting to enforce camera Policy")
        enableCameraPolicy(context.applicationContext)
        Log.d(TAG,"Enforced Camera Policy")
    }
    private fun enablePackagePolicy(context: Context){
        Log.d(TAG,"Enabling Package Policy")
        val packagePref = PackagePrefManager(context)
        val packageList = PackageData(context,PackageManager.MATCH_DISABLED_COMPONENTS,false).returnData()
        for(app in packageList){
            runBlocking {
                if(packagePref.readEnabled(app.packageName) == 2){
                    dpm.setApplicationHidden(cn,app.packageName,true)
                    Log.d(TAG,"Disabled " + app.packageName?.toString())
                }
            }
        }
    }
    private fun enablePermissionPolicy(context: Context){
        Log.d(TAG,"Enabling Permission Policy")
        val permissionD = PermissionData(context)
        val permissionPref = PermissionPrefManager(context)
        permissionD.populateData()
        for(permissions in permissionD.returnData()){
            runBlocking {
                if(permissionPref.readPermEnabled(permissions.first) == 2){
                    dpm.addUserRestriction(cn,permissions.first)
                }
            }
        }
    }
    private fun enableCameraPolicy(context: Context){
        Log.d(TAG,"Enabling Camera Policy")
        val miscPref = MiscPrefManager(context)
        runBlocking {
            if(miscPref.readEnabled("camera") == 1){
                dpm.setCameraDisabled(cn,true)
            }
        }
    }

    private fun enablePasswordPolicy(context: Context){
        Log.d(TAG,"Checking compliance to Password standards")
        dpm.requiredPasswordComplexity = DevicePolicyManager.PASSWORD_COMPLEXITY_HIGH
        while(!dpm.isActivePasswordSufficientForDeviceRequirement){
            Toast.makeText(context,"Set a better password",Toast.LENGTH_LONG).show()
            val passIntent = Intent(context,DevicePolicyManager.ACTION_SET_NEW_PASSWORD::class.java)
            context.applicationContext.startActivity(passIntent)
        }
        val maxWipeTries = runBlocking {
            MiscPrefManager(context.applicationContext).readEnabled("wipe_retries")
        }
        Log.d(TAG,"Setting Maximum Failed Password Limit")
        dpm.setMaximumFailedPasswordsForWipe(cn,maxWipeTries?:0)
        Log.d(TAG,"Successfully Set Maximum Failed Password Limit")
    }

}