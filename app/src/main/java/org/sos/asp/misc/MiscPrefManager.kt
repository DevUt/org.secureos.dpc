package org.sos.asp.misc

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

val Context.miscData : DataStore<Preferences> by preferencesDataStore(name = "misc")

class MiscPrefManager(context: Context) {
    private val dataStore: DataStore<Preferences> = context.miscData
    suspend fun readEnabled(miscName: String): Int {
        Log.i("MiscItem", "entered readEnabled for $miscName")
        val miscKey = intPreferencesKey(miscName)
        val value = dataStore.data.map { preferences ->
            preferences[miscKey] ?: 0 // Provide a default value
        }.first() // Collect the first emitted value and then cancel the flow
        Log.i("MiscItem", "in readEnabled $value $miscName")
        return value
    }
    // 0-> not set 1-> enabled 2-> disabled
    suspend fun writeEnabled(miscName: String, enabled: Int) {
        val miscKey = intPreferencesKey(miscName)
        dataStore.edit { preferences ->
            preferences[miscKey] = enabled
            Log.i("MiscItem", "$miscName ${preferences[miscKey]} Intended : $enabled")
        }
    }
}