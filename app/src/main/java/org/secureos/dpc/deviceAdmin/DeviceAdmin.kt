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
        Log.d(TAG,"Enabling package policy")
        enablePackagePolicy(context.applicationContext)
        Log.d(TAG,"Enabled package policy")
        Log.d(TAG,"Enabling permission policy")
        enablePermissionPolicy(context.applicationContext)
    }
    private fun enablePackagePolicy(context: Context){
        Log.d(TAG,"Enabling Package Policy")
        val packagePref = PackagePrefManager(context)
        val packageList = context.packageManager.getInstalledApplications(PackageManager.MATCH_DISABLED_COMPONENTS)
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
}