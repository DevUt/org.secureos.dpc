package org.sos.asp.bootWork

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import kotlinx.coroutines.runBlocking
import org.sos.asp.deviceAdmin.DeviceAdmin
import org.sos.asp.misc.MiscPrefManager

class BootEnforcementCheck : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val miscPrefManager = MiscPrefManager(context.applicationContext)
        runBlocking {
            if(DeviceAdmin().isAdmin(context.applicationContext)){
                if(miscPrefManager.readEnabled("enforced") == 2){
                    val deviceAdmin = DeviceAdmin()
                    deviceAdmin.enforcePolicy(context.applicationContext);
                }
            }
        }

    }

}