package org.secureos.dpc.misc

import android.content.Context
import kotlinx.coroutines.runBlocking

class MiscData(context: Context) {
    private val miscPrefManager = MiscPrefManager(context)

    fun enforceDefaults(){
        runBlocking {
            miscPrefManager.writeEnabled("wipe_retries",7)
            miscPrefManager.writeEnabled("min_length",18)
            miscPrefManager.writeEnabled("min_uppercase",4)
            miscPrefManager.writeEnabled("min_lowercase",6)
            miscPrefManager.writeEnabled("min_special",4)
            miscPrefManager.writeEnabled("min_number",4)
            miscPrefManager.writeEnabled("vpn_always",2)
            miscPrefManager.writeEnabled("vpn_lockdown",2)
        }
    }
}