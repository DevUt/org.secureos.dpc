package org.secureos.dpc.permissionManagement

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow

val Context.permissionData : DataStore<Preferences> by preferencesDataStore(name = "permission")
class PermissionPrefManager(context: Context){
    private var permissionFlowRead : Flow<Preferences> = context.permissionData.data
    private var permissionFlowWrite = context.permissionData
    suspend fun readPermEnabled(permissionName : String) : Int?{
        val permissionKey = intPreferencesKey(permissionName)
        var retval : Int = 0
        permissionFlowWrite.edit {
                pref->
//            pref[permissionKey] = enabled
            retval = pref[permissionKey] ?:0
//            Log.i("permissionPref",permissionName + " " + pref[permissionKey].toString() + " Intended : " + enabled.toString())
        }
        return retval
    }
    // 0-> not set 1-> enabled 2-> disabled
    suspend fun writePermEnabled(permissionName: String,enabled : Int) {
        val permissionKey = intPreferencesKey(permissionName)
        permissionFlowWrite.edit {
                pref->
            pref[permissionKey] = enabled
            Log.i("permissionPref",permissionName + " " + pref[permissionKey].toString() + " Intended : " + enabled.toString())
        }
    }
}