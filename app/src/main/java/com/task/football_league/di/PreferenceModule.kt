package com.task.football_league.di


import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class PreferenceModule @Inject
constructor(@ApplicationContext context: Context) {
    private val preferences: SharedPreferences =
        context.getSharedPreferences("FootballLeg_preference", Context.MODE_PRIVATE)

    //handle string
    private fun putString(key: String, value: String?) {
        val editor = preferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    private fun getString(key: String, defaultValue: String?): String? {
        return preferences.getString(key, defaultValue)
    }

    //language
    var language: String
        get() = this.getString("language", "en").toString()
        set(currentLanguage) = putString("language", currentLanguage)

    //token
    var token: String
        get() = this.getString("token", "").toString()
        set(token) = putString("token", token)



}
