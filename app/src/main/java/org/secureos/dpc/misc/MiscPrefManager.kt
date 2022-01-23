package org.secureos.dpc.misc

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow

val Context.miscData : DataStore<Preferences> by preferencesDataStore(name = "misc")

class MiscPrefManager(context: Context) {
    private var miscFlowRead : Flow<Preferences> = context.miscData.data
    private var miscFlowWrite = context.miscData
    suspend fun readEnabled(miscName : String) : Int?{
        Log.i("MiscItem", "entered readEnabled")
        val miscKey = intPreferencesKey(miscName)
        var retval : Int = 0
        miscFlowWrite.edit {
                pref ->
            retval = pref[miscKey] ?: 0
        }
//        Log.i("MiscItem", "in readEnabled " + retval.toString() + " "+ miscName.toString())
        return retval
    }
    // 0-> not set 1-> enabled 2-> disabled
    suspend fun writeEnabled(miscName: String,enabled : Int) {
        val miscKey = intPreferencesKey(miscName)
        miscFlowWrite.edit {
                pref->
            pref[miscKey] = enabled
            Log.i("MiscItem",miscName + " " + pref[miscKey].toString() + " Intended : " + enabled.toString())
        }
    }
    
    
}