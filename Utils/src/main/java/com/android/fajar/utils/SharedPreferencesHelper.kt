package com.android.fajar.utils

import android.content.Context

open class SharedPreferencesHelper {
    open fun putString(var0: Context, var1: String?, var2: String?) {
        Helper.checkPermission()
        val var3 = var0.getSharedPreferences(Helper.getConfig().getSharedPreferencesName(var0), 0)
        val var4 = var3.edit()
        var4.putString(var1, var2)
        var4.commit()
    }

    open fun getString(var0: Context, var1: String?): String? {
        Helper.checkPermission()
        val var2 = var0.getSharedPreferences(Helper.getConfig().getSharedPreferencesName(var0), 0)
        return var2.getString(var1, null as String?)
    }

    open fun putInt(var0: Context, var1: String?, var2: Int) {
        Helper.checkPermission()
        val var3 = var0.getSharedPreferences(Helper.getConfig().getSharedPreferencesName(var0), 0)
        val var4 = var3.edit()
        var4.putInt(var1, var2)
        var4.commit()
    }

    open fun getInt(var0: Context, var1: String?): Int {
        Helper.checkPermission()
        val var2 = var0.getSharedPreferences(Helper.getConfig().getSharedPreferencesName(var0), 0)
        return var2.getInt(var1, -1)
    }

    open fun getFloat(var0: Context, var1: String?): Double {
        Helper.checkPermission()
        val var2 = var0.getSharedPreferences(Helper.getConfig().getSharedPreferencesName(var0), 0)
        return var2.getFloat(var1, -1.0f).toDouble()
    }

    open fun putFloat(var0: Context, var1: String?, var2: Float) {
        Helper.checkPermission()
        val var3 = var0.getSharedPreferences(Helper.getConfig().getSharedPreferencesName(var0), 0)
        val var4 = var3.edit()
        var4.putFloat(var1, var2)
        var4.commit()
    }

    open fun putBoolean(var0: Context, var1: String?, var2: Boolean) {
        Helper.checkPermission()
        val var3 = var0.getSharedPreferences(Helper.getConfig().getSharedPreferencesName(var0), 0)
        val var4 = var3.edit()
        var4.putBoolean(var1, var2)
        var4.commit()
    }

    open fun getBoolean(var0: Context, var1: String?): Boolean {
        Helper.checkPermission()
        val var2 = var0.getSharedPreferences(Helper.getConfig().getSharedPreferencesName(var0), 0)
        return var2.getBoolean(var1, false)
    }

    open fun removePreferenceData(var0: Context, var1: String?) {
        Helper.checkPermission()
        val var2 = var0.getSharedPreferences(Helper.getConfig().getSharedPreferencesName(var0), 0)
        var2.edit().remove(var1).commit()
    }
}