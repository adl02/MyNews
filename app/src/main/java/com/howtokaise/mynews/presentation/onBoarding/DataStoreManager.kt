//package com.howtokaise.mynews.data.local
//
//import android.content.Context
//import androidx.datastore.preferences.core.booleanPreferencesKey
//import androidx.datastore.preferences.core.edit
//import androidx.datastore.preferences.preferencesDataStore
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.map
//
//// DataStore instance (outside class)
//val Context.dataStore by preferencesDataStore(name = "settings")
//
//class DataStoreManager(private val context: Context) {
//
//    companion object {
//        private val ONBOARDING_KEY = booleanPreferencesKey("onboarding_completed")
//    }
//
//    // Save onboarding state
//    suspend fun saveOnboardingState(completed: Boolean) {
//        context.dataStore.edit { preferences ->
//            preferences[ONBOARDING_KEY] = completed
//        }
//    }
//
//    // Read onboarding state
//    val readOnboardingState: Flow<Boolean> = context.dataStore.data
//        .map { preferences ->
//            preferences[ONBOARDING_KEY] ?: false
//        }
//}
