package org.sos.asp.bootWork

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import org.sos.asp.deviceAdmin.DeviceAdmin

class bootAction : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val deviceObj = DeviceAdmin()
        if(context != null) {
            if (deviceObj.isAdmin(context)) {
                Log.i("BootAction","Told to enforce to the policy");
                deviceObj.enforcePolicy(context);
            }
        }else{
            Log.e("BootAction","Fatal Error in Context");
        }
    }

}