package com.example.homew3.cleanArch.presentation.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class SharedPreferencesManager(context: Context) {

    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    var isLogined: Boolean
        get() = prefs.getBoolean(KEY_LOGINED, false)
        set(value) {
            prefs.edit {
                putBoolean(KEY_LOGINED, value)
            }
        }


    var userId by StringPrefsDelegate(prefs, KEY_ID)

    var recipeId by StringPrefsDelegate(prefs, KEY_RECIPE_ID)


    companion object {
        private const val PREFS_NAME = "prefs"
        private const val KEY_LOGINED = "key_switch"
        private const val KEY_ID = "key_id"
        private const val KEY_RECIPE_ID = "key_id_recipe"


    }
}

class StringPrefsDelegate(
    private val prefs: SharedPreferences,
    private val key: String
) : ReadWriteProperty<Any, String> {

    override fun getValue(thisRef: Any, property: KProperty<*>): String {
        return prefs.getString(key, "") ?: ""
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: String) {
        prefs.edit {
            putString(key, value)
        }
    }
}

