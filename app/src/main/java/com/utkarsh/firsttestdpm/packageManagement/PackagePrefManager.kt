package com.utkarsh.firsttestdpm.packageManagement

import android.content.Context
import android.util.Log
import android.util.Log.INFO
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.packageData : DataStore<Preferences> by preferencesDataStore(name = "enabled")
class PackagePrefManager(context: Context){
    private var packageFlowRead : Flow<Preferences> = context.packageData.data
    private var packageFlowWrite = context.packageData
    suspend fun readEnabled(packageName : String) : Int?{
        Log.i("PackageItem", "entered readEnabled")
        val packageKey = intPreferencesKey(packageName)
        var retval : Int = 0
        packageFlowWrite.edit {
            pref ->
                retval = pref[packageKey] ?: 0
        }
        Log.i("PackageItem", "in readEnabled " + retval.toString() + " "+ packageName.toString())
        return retval
    }
    // 0-> not set 1-> enabled 2-> disabled
    suspend fun writeEnabled(packageName: String,enabled : Int) {
        val packageKey = intPreferencesKey(packageName)
        packageFlowWrite.edit {
            pref->
                pref[packageKey] = enabled
            Log.i("PackageItem",packageName + " " + pref[packageKey].toString() + " Intended : " + enabled.toString())
        }
    }

}