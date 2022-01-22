package org.secureos.dpc.deviceAdmin

import android.app.admin.DeviceAdminReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import org.secureos.dpc.permissionManagement.PermissionActivity
import org.secureos.dpc.policyEnforcer.PolicyEnforcer

class DeviceAdmin : DeviceAdminReceiver(){
    companion object{
        const val TAG = "deviceAdmin"
    }
    override fun onEnabled(context: Context, intent: Intent) {
        super.onEnabled(context, intent)
        Log.d(TAG,"enabled")
        val policyEnforcerIntent = Intent(context.applicationContext,PolicyEnforcer::class.java)
        policyEnforcerIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.applicationContext.startActivity(policyEnforcerIntent)
    }

}