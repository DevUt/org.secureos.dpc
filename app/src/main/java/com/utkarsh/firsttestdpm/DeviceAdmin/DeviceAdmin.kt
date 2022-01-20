package com.utkarsh.firsttestdpm.DeviceAdmin

import android.app.admin.DeviceAdminReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.util.Log


class DeviceAdmin : DeviceAdminReceiver(){
    companion object{
        const val TAG = "DeviceAdmin"
    }
    override fun onEnabled(context: Context, intent: Intent) {
        super.onEnabled(context, intent)
        Log.d(TAG,"enabled")
    }
}