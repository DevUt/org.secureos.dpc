package org.secureos.dpc.deviceAdmin

import android.app.admin.DeviceAdminReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class DeviceAdmin : DeviceAdminReceiver(){
    companion object{
        const val TAG = "deviceAdmin"
    }
    override fun onEnabled(context: Context, intent: Intent) {
        super.onEnabled(context, intent)
        Log.d(TAG,"enabled")
    }

}