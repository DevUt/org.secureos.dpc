package org.secureos.dpc.misc

import android.content.Context
import kotlinx.coroutines.runBlocking

class MiscData(context: Context) {
    private val miscPrefManager = MiscPrefManager(context)

    fun enforceDefaults(){
        runBlocking {
            miscPrefManager.writeEnabled("wipe_retries",7)
            miscPrefManager.writeEnabled("min_length",20)
            miscPrefManager.writeEnabled("min_uppercase",1)
            miscPrefManager.writeEnabled("min_lowercase",1)
            miscPrefManager.writeEnabled("min_special",1)
            miscPrefManager.writeEnabled("min_number",1)
        }
    }
}