package com.utkarsh.firsttestdpm.DeviceAdmin

import android.app.admin.DeviceAdminReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

const val TAG = "device_admin"
class device_admin : DeviceAdminReceiver(){
    override fun onEnabled(context: Context, intent: Intent) {
        super.onEnabled(context, intent)
        Log.d(TAG,"enabled")
    }
}