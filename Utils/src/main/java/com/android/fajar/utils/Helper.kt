package com.android.fajar.utils

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Point
import android.net.ConnectivityManager
import android.os.Build
import android.provider.Settings
import android.telephony.TelephonyManager
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.bumptech.glide.Glide
import com.google.gson.*
import com.j256.ormlite.android.apptools.OpenHelperManager
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.lang.NullPointerException
import java.lang.RuntimeException
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by
 * Fajar Anugrah Ramadhan License
 * ===============================================
 *
 * Copyright (C).
 * All right reserved
 *
 * Name      : Fajar Anugrah Ramadhan
 * E-mail    : fajarconan@gmail.com
 * Github    : https://github.com/fajaranugrah
 * LinkedIn  : linkedin.com/in/fajar-anugrah
 *
 */

open class Helper() {
    companion object {
        private val a: Array<String>
        private var b: Context? = null
        private var c: Config? = null
        private var d = false
        var dbHelper: DatabaseHelper? = null
        private var e: Toast? = null
        const val MAIN_FRAGMENT_TAG = "main"
        fun initialize(var0: Context?) {
            b = var0
            val var1 = a
            val var2 = var1.size
            for (var3 in 0 until var2) {
                val var4 = var1[var3]
                if (var4 == b!!.packageName) {
                    d = var4 == b!!.packageName
                }
            }
        }

        fun checkPermission(): Boolean {
            return if (d) {
                true
            } else {
                throw RuntimeException("Can not use Helper.")
            }
        }

        @JvmStatic
        var config: Config?
            get() = if (c != null) {
                c
            } else {
                throw RuntimeException("Config not found. Have you set your config using setConfig()?")
            }
            set(var0) {
                c = var0
            }

        fun getHelper(var0: Context?): DatabaseHelper? {
            checkPermission()
            if (dbHelper == null) {
                dbHelper = DatabaseHelper(var0)
            }
            return dbHelper
        }

        fun releaseHelper() {
            checkPermission()
            OpenHelperManager.releaseHelper()
            dbHelper!!.close()
            dbHelper = null
        }

        val gson: Gson
            get() {
                checkPermission()
                val var0 = GsonBuilder()
                var0.excludeFieldsWithModifiers(*intArrayOf(16, 128, 8, 2))
                var0.registerTypeAdapter(Date::class.java, JsonDeserializer { var1, var2, var3 ->
                    try {
                        return@JsonDeserializer SimpleDateFormat(config!!.apiDateFormat)
                            .parse(var1.asJsonPrimitive.asString)
                    } catch (var7: ParseException) {
                        try {
                            return@JsonDeserializer Date(var1.asJsonPrimitive.asLong * 1000L)
                        } catch (var6: Exception) {
                            return@JsonDeserializer null
                        }
                    }
                })
                var0.registerTypeAdapter(
                    java.lang.Boolean.TYPE,
                    JsonDeserializer { var1, var2, var3 ->
                        try {
                            return@JsonDeserializer var1.asInt == 1
                        } catch (var5: Exception) {
                            return@JsonDeserializer var1.asBoolean
                        }
                    })
                var0.setDateFormat(config!!.apiDateFormat)
                var0.setFieldNamingPolicy(config!!.fieldNamingPolicy)
                try {
                    config!!.postProcessGsonBuilder(var0)
                } catch (var2: Exception) {
                    var2.printStackTrace()
                }
                return var0.create()
            }
        val gsonIncludePrivate: Gson
            get() {
                checkPermission()
                val var0 = GsonBuilder()
                var0.excludeFieldsWithModifiers(*intArrayOf(16, 128, 8))
                var0.registerTypeAdapter(Date::class.java, JsonDeserializer { var1, var2, var3 ->
                    try {
                        return@JsonDeserializer SimpleDateFormat(config!!.apiDateFormat)
                            .parse(var1.asJsonPrimitive.asString)
                    } catch (var5: ParseException) {
                        return@JsonDeserializer null
                    }
                })
                var0.registerTypeAdapter(
                    java.lang.Boolean.TYPE,
                    JsonDeserializer { var1, var2, var3 -> var1.asInt == 1 })
                var0.setDateFormat(config!!.apiDateFormat)
                var0.setFieldNamingPolicy(config!!.fieldNamingPolicy)
                try {
                    config!!.postProcessGsonBuilder(var0)
                } catch (var2: Exception) {
                    var2.printStackTrace()
                }
                return var0.create()
            }

        fun <T> getService(var0: Context?, var1: Class<T>): T {
            val var2 = config!!.getOkHttpClient(var0)
            val var3 = Retrofit.Builder().baseUrl(config!!.serverIpAddress).client(var2)
            try {
                val var4: Iterator<*> = getConverterFactoryList(b).iterator()
                while (var4.hasNext()) {
                    val var5 = var4.next() as Converter.Factory
                    var3.addConverterFactory(var5)
                }
            } catch (var6: Exception) {
                var6.printStackTrace()
            }
            var3.addConverterFactory(NullOnEmptyConverterFactory()).addConverterFactory(
                GsonConverterFactory.create(
                    gson
                )
            )
            val var7 = var3.build()
            return var7.create(var1)
        }

        fun getConverterFactoryList(var0: Context?): List<Converter.Factory?> {
            return ArrayList()
        }

        fun bindImage(var0: Context?, var1: ImageView?, var2: String?, var3: Int) {
            checkPermission()
            try {
                Glide.with(var0!!).load(var2).error(var3).into(var1!!)
            } catch (var5: Exception) {
                var5.printStackTrace()
            }
        }

        fun bindImage(var0: ImageView, var1: String?, var2: Int) {
            checkPermission()
            try {
                Glide.with(var0.context).load(var1).error(var2).into(var0)
            } catch (var4: Exception) {
                var4.printStackTrace()
            }
        }

        fun bindImage(var0: ImageView, var1: String?, var2: Int, var3: Int) {
            checkPermission()
            try {
                Glide.with(var0.context).load(var1).error(var2).placeholder(var3).into(var0)
            } catch (var5: Exception) {
                var5.printStackTrace()
            }
        }

        fun formatDate(var0: Date?): String {
            val var1 = SimpleDateFormat(config!!.dateFormat)
            return var1.format(var0)
        }

        fun formatTime(var0: Date?): String {
            val var1 = SimpleDateFormat(config!!.timeFormat)
            return var1.format(var0)
        }

        fun formatDateTime(var0: Date?): String {
            val var1 = SimpleDateFormat(config!!.dateTimeFormat)
            return var1.format(var0)
        }

        fun parseTime(var0: Date): String {
            checkPermission()
            val var1 = "%dd ago"
            val var2 = "%dh ago"
            val var3 = "%dm ago"
            val var4 = "%ds ago"
            val var5 = "Just Now"
            val var6 = Date()
            val var7 = var6.time - var0.time
            val var9 = var7 / 3600000L
            val var11 = var7 / 60000L % 60L
            val var13 = var7 / 1000L % 60L
            return if (var9 > 0L) if (var9 / 24L > 0L) String.format(
                var1,
                var9.toInt() / 24
            ) else String.format(var2, var9.toInt()) else if (var11 > 0L) String.format(
                var3,
                var11.toInt()
            ) else if (var13 > 0L) String.format(var4, var13.toInt()) else var5
        }

        fun capitalizeString(var0: String): String {
            checkPermission()
            val var1 = var0.toLowerCase().toCharArray()
            var var2 = false
            for (var3 in var1.indices) {
                if (!var2 && Character.isLetter(var1[var3])) {
                    var1[var3] = Character.toUpperCase(var1[var3])
                    var2 = true
                } else if (Character.isWhitespace(var1[var3]) || var1[var3] == '.' || var1[var3] == '\'') {
                    var2 = false
                }
            }
            return String(var1)
        }

        fun copyToClipboard(var0: Context, var1: String?, var2: String?) {
            try {
                val var3 = var0.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val var4 = ClipData.newPlainText(var1, var2)
                var3.setPrimaryClip(var4)
            } catch (var5: Exception) {
                var5.printStackTrace()
            }
        }

        @JvmStatic
        fun copyToClipboard(var0: Context, var1: String?) {
            try {
                val var2 = var0.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val var3 = ClipData.newPlainText("", var1)
                var2.setPrimaryClip(var3)
            } catch (var4: Exception) {
                var4.printStackTrace()
            }
        }

        fun getScreenSize(var0: Context): Point {
            checkPermission()
            val var1 = var0.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val var2 = var1.defaultDisplay
            val var3 = Point()
            try {
                var2.getSize(var3)
            } catch (var5: NoSuchMethodError) {
                var3.x = var2.width
                var3.y = var2.height
            }
            return var3
        }

        fun shakeView(var0: View?) {
            //YoYo.with(Techniques.Shake).duration((long)getConfig().getDefaultAnimationDuration()).playOn(var0);
        }

        fun toast(var0: Context?, var1: String?) {
            checkPermission()
            try {
                e!!.cancel()
            } catch (var3: NullPointerException) {
            } catch (var4: Exception) {
                var4.printStackTrace()
            }
            val ae = Toast.makeText(var0, var1, Toast.LENGTH_LONG)
            ae.show()
            e = ae
        }

        @JvmStatic
        fun toast(var0: Context?, var1: Int) {
            checkPermission()
            try {
                e!!.cancel()
            } catch (var3: NullPointerException) {
            } catch (var4: Exception) {
                var4.printStackTrace()
            }
            val ae = Toast.makeText(var0, var1, Toast.LENGTH_LONG)
            ae.show()
            e = ae
        }

        fun isOnline(var0: Context): Boolean {
            val var1 = var0.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val var2 = var1.activeNetworkInfo
            return var2 != null && var2.isConnectedOrConnecting
        }

        /*public static String MD5(String var0) {
        checkPermission();
        return new String(a.a.a.a.a.a.a(a.a.a.a.b.a.b(var0)));
    }

    public static String SHA1(String var0) {
        checkPermission();
        return new String(a.a.a.a.a.a.a(a.a.a.a.b.a.c(var0)));
    }*/
        fun addFragment(var0: FragmentManager, var1: Fragment?) {
            checkPermission()
            if (config!!.defaultFragmentContainerResId != -1) {
                var0.beginTransaction().replace(config!!.defaultFragmentContainerResId, var1!!)
                    .addToBackStack("main").commit()
            } else {
                log(
                    Helper::class.java.simpleName + ".addFragment",
                    "Fragment Container Res ID Not found. Please implemented Config.getDefaultFragmentContainerResId() to use this function"
                )
                throw RuntimeException(Helper::class.java.simpleName + ".addFragment | Fragment Container Res ID Not found. Please implemented Config.getDefaultFragmentContainerResId() to use this function")
            }
        }

        fun changeFragment(var0: FragmentManager, var1: Fragment?) {
            checkPermission()
            while (var0.popBackStackImmediate()) {
            }
            if (config!!.defaultFragmentContainerResId != -1) {
                var0.beginTransaction().replace(config!!.defaultFragmentContainerResId, var1!!)
                    .commit()
            } else {
                log(
                    Helper::class.java.simpleName + ".changeFragment",
                    "Fragment Container Res ID Not found. Please implemented Config.getDefaultFragmentContainerResId() to use this function"
                )
                throw RuntimeException(Helper::class.java.simpleName + ".addFragment | Fragment Container Res ID Not found. Please implemented Config.getDefaultFragmentContainerResId() to use this function")
            }
        }

        fun getImei(var0: Context?): String {
            checkPermission()
            var var1 = ""
            try {
                val var2 = var0!!.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                var1 = var2.deviceId
            } catch (var3: Exception) {
                var3.printStackTrace()
            }
            return var1
        }

        fun getAndroidId(var0: Context): String {
            checkPermission()
            val var1 = getImei(b)
            var var2: String? = ""
            try {
                var2 = Settings.Secure.getString(var0.contentResolver, "android_id")
            } catch (var4: Exception) {
                var4.printStackTrace()
            }
            return String.format("%s = %s", var1, var2)
        }

        @JvmStatic
        fun log(var0: Any, var1: String?) {
            checkPermission()
            try {
                val var2: String
                var2 = if (var0 is String) {
                    var0
                } else {
                    var0.javaClass.simpleName
                }
                if (var1 != null) {
                    Log.v(var2, var1)
                } else {
                    Log.v(var2, "Message is null!")
                }
            } catch (var3: Exception) {
                var3.printStackTrace()
            }
        }

        fun parseToApiDate(var0: Date?): String {
            checkPermission()
            return SimpleDateFormat(config!!.apiDateFormat)
                .format(var0)
        }

        fun formatCurrency(var0: Double): String {
            checkPermission()
            return DecimalFormat(config!!.currencyFormat)
                .format(var0)
        }

        fun formatCurrency(var0: Double, var2: DecimalFormatSymbols?): String {
            checkPermission()
            return DecimalFormat(config!!.currencyFormat, var2)
                .format(var0)
        }

        fun formatDecimal(var0: Double): String {
            checkPermission()
            return DecimalFormat(config!!.decimalFormat)
                .format(var0)
        }

        fun formatDecimal(var0: Double, var2: DecimalFormatSymbols?): String {
            checkPermission()
            return DecimalFormat(config!!.decimalFormat, var2)
                .format(var0)
        }

        fun checkPermission(var0: Context?, var1: String?): Boolean {
            return if (Build.VERSION.SDK_INT < 23) {
                true
            } else {
                val var2 = ContextCompat.checkSelfPermission(var0!!, var1!!)
                var2 == 0
            }
        }

        /*public static boolean checkPermission(final Context var0, String var1, boolean var2) {
        if (checkPermission(var0, var1)) {
            return true;
        } else {
            try {
                if (var2) {
                    (new CustomConfirmationDialog(var0, R.string.dialog_request_permission_title, String.format(var0.getString(string.dialog_request_permission_content), var1), string.dialog_request_permission_positive_text, string.dialog_request_permission_negative_text, new ButtonCallback() {
                        public void onNegative(MaterialDialog var1) {
                            super.onPositive(var1);
                            Intent var2 = new Intent("android.intent.action.VIEW");
                            var2.setData(Uri.parse(Helper.getConfig().getPermissionDetailsUrl()));
                            var0.startActivity(var2);
                        }
                    })).show();
                }
            } catch (Exception var4) {
                var4.printStackTrace();
            }

            return false;
        }
    }*/
        fun hideSoftKeyboard(var0: Activity) {
            try {
                val var1 = var0.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                var1.hideSoftInputFromWindow(var0.currentFocus!!.windowToken, 2)
            } catch (var2: Exception) {
                var2.printStackTrace()
            }
        }

        init {
            a = CustomConfig.PACKAGE_NAMES
            b = null
            c = null
            d = false
            dbHelper = null
        }
    }
}