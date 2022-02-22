package org.secureos.dpc.emergencyPolicyUpdation

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.UserManager
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.runBlocking
import org.secureos.dpc.deviceAdmin.DeviceAdmin
import org.secureos.dpc.permissionManagement.PermissionPrefManager

class EmergencyPolicyChange : BroadcastReceiver(){
    companion object{
        val TAG = "EmergencyPolicyChange"
    }
    override fun onReceive(context: Context, intent: Intent) {
        Toast.makeText(context,"App updated!",Toast.LENGTH_LONG);
        val permissionPerfManager = PermissionPrefManager(context);
        runBlocking {
            permissionPerfManager.writePermEnabled("emergency_policy_version",0);
            permissionPerfManager.writePermEnabled(UserManager.DISALLOW_MODIFY_ACCOUNTS,1);
        }
        Log.d(TAG,"Trying to enable Emergency Policy Permission Update")
        val emergencyPolicyEnforcement = DeviceAdmin()
        emergencyPolicyEnforcement.initialize(context)
        emergencyPolicyEnforcement.enablePermissionPolicy(context)
        Log.d(TAG,"Enabled Emergency Policy Permission Update")
    }
}