package com.android.fajar.utils

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.annotation.IdRes
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
//import com.j256.ormlite.support.ConnectionSource
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.ArrayList
import java.util.concurrent.TimeUnit

abstract class Config {
    abstract val defaultAnimationDuration: Int
    abstract val databaseName: String?
    abstract val databaseVersion: Int
    abstract val listModelForDatabase: Array<Class<*>?>?

    abstract fun onUpgradeDatabase(
        var1: SQLiteDatabase?,
        var3: Int,
        var4: Int
    )

    abstract val apiDateFormat: String?
    abstract val fieldNamingPolicy: FieldNamingPolicy?

    @get:IdRes
    val defaultFragmentContainerResId: Int
        get() = -1
    abstract val currencyFormat: String?
    abstract val decimalFormat: String?

    abstract fun getSharedPreferencesName(var1: Context?): String?
    abstract fun getDefaultProgressDialogTitle(var1: Context?): String?
    abstract fun getDefaultProgressDialogContent(var1: Context?): String?
    abstract val requestHttpTimeOutInMillis: Long
    abstract val serverIpAddress: String?
    val logLevel: HttpLoggingInterceptor.Level
        get() = HttpLoggingInterceptor.Level.NONE

    @Deprecated("")
    fun getRequestInterceptor(var1: Context?): Interceptor {
        return Interceptor { var1 ->
            val var2 = var1.request().newBuilder().build()
            var1.proceed(var2)
        }
    }

    fun getRequestInterceptorList(var1: Context?): ArrayList<*> {
        val var2: ArrayList<*> = ArrayList<Any?>()
        var2.add(getRequestInterceptor(var1) as Nothing)
        return var2
    }

    val permissionDetailsUrl: String
        get() = "http://www.howtogeek.com/230683/how-to-manage-app-permissions-on-android-6.0/"
    abstract val dateFormat: String?
    abstract val timeFormat: String?
    abstract val dateTimeFormat: String?

    @get:Deprecated("")
    val errorLoggerProjectId: String?
        get() = null

    @get:Deprecated("")
    val errorLoggerApiKey: String?
        get() = null

    @get:Deprecated("")
    val errorLoggerIpAddress: String?
        get() = null

    fun postProcessGsonBuilder(var1: GsonBuilder?) {}
    fun getOkHttpClient(var1: Context?): OkHttpClient {
        val var2: OkHttpClient.Builder = getOkHttpClientBuilder(var1)
        var2.readTimeout(Helper.config?.requestHttpTimeOutInMillis!!, TimeUnit.MILLISECONDS)
        var2.writeTimeout(Helper.config?.requestHttpTimeOutInMillis!!, TimeUnit.MILLISECONDS)
        val var3: Iterator<*> = getRequestInterceptorList(var1).iterator()
        while (var3.hasNext()) {
            val var4 = var3.next() as Interceptor
            var2.addInterceptor(var4)
        }
        val var5 = HttpLoggingInterceptor()
        var5.setLevel(logLevel)
        var2.addInterceptor(var5).build()
        return var2.build()
    }

    fun getOkHttpClientBuilder(var1: Context?): OkHttpClient.Builder {
        return OkHttpClient.Builder()
    }
}