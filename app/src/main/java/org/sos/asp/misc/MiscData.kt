package org.sos.asp.misc

import android.content.Context
import kotlinx.coroutines.runBlocking

class MiscData(context: Context) {
    private val miscPrefManager = MiscPrefManager(context)

    fun enforceDefaults(){
        runBlocking {
            miscPrefManager.writeEnabled("wipe_retries",7)
            miscPrefManager.writeEnabled("min_length",10)
            miscPrefManager.writeEnabled("min_uppercase",2)
            miscPrefManager.writeEnabled("min_lowercase",2)
            miscPrefManager.writeEnabled("min_special",2)
            miscPrefManager.writeEnabled("min_number",2)
            miscPrefManager.writeEnabled("vpn_always",2)
            miscPrefManager.writeEnabled("vpn_lockdown",2)
            miscPrefManager.writeEnabled("block_vpn_uninstall",0)
        }
    }
}