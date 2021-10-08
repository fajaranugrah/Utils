package com.android.fajar.utils;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HelperJav {
    private static final String[] a;
    private static Context b;
    private static Config c;
    private static boolean d;
    public static DatabaseHelperJav dbHelper;
    private static Toast e;
    public static final String MAIN_FRAGMENT_TAG = "main";

    public HelperJav() {
    }

    public static void initialize(Context var0) {
        b = var0;
        String[] var1 = a;
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            String var4 = var1[var3];
            if (var4.equals(b.getPackageName())) {
                d = var4.equals(b.getPackageName());
            }
        }

    }

    public static void setConfig(Config var0) {
        c = var0;
    }

    public static final boolean checkPermission() {
        if (d) {
            return true;
        } else {
            throw new RuntimeException("Can not use Helper.");
        }
    }

    public static Config getConfig() {
        if (c != null) {
            return c;
        } else {
            throw new RuntimeException("Config not found. Have you set your config using setConfig()?");
        }
    }

    public static final DatabaseHelperJav getHelper(Context var0) {
        checkPermission();
        if (dbHelper == null) {
            dbHelper = new DatabaseHelperJav(var0);
        }

        return dbHelper;
    }

    public static void releaseHelper() {
        checkPermission();
        OpenHelperManager.releaseHelper();
        dbHelper.close();
        dbHelper = null;
    }

    public static Gson getGson() {
        checkPermission();
        GsonBuilder var0 = new GsonBuilder();
        var0.excludeFieldsWithModifiers(new int[]{16, 128, 8, 2});
        var0.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            public Date deserialize(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
                try {
                    return (new SimpleDateFormat(HelperJav.getConfig().getApiDateFormat())).parse(var1.getAsJsonPrimitive().getAsString());
                } catch (ParseException var7) {
                    try {
                        return new Date(var1.getAsJsonPrimitive().getAsLong() * 1000L);
                    } catch (Exception var6) {
                        return null;
                    }
                }
            }
        });
        var0.registerTypeAdapter(Boolean.TYPE, new JsonDeserializer<Boolean>() {
            public Boolean deserialize(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
                try {
                    return var1.getAsInt() == 1;
                } catch (Exception var5) {
                    return var1.getAsBoolean();
                }
            }
        });
        var0.setDateFormat(getConfig().getApiDateFormat());
        var0.setFieldNamingPolicy(getConfig().getFieldNamingPolicy());

        try {
            getConfig().postProcessGsonBuilder(var0);
        } catch (Exception var2) {
            var2.printStackTrace();
        }

        return var0.create();
    }

    public static Gson getGsonIncludePrivate() {
        checkPermission();
        GsonBuilder var0 = new GsonBuilder();
        var0.excludeFieldsWithModifiers(new int[]{16, 128, 8});
        var0.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            public Date deserialize(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
                try {
                    return (new SimpleDateFormat(HelperJav.getConfig().getApiDateFormat())).parse(var1.getAsJsonPrimitive().getAsString());
                } catch (ParseException var5) {
                    return null;
                }
            }
        });
        var0.registerTypeAdapter(Boolean.TYPE, new JsonDeserializer<Boolean>() {
            public Boolean deserialize(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
                return var1.getAsInt() == 1;
            }
        });
        var0.setDateFormat(getConfig().getApiDateFormat());
        var0.setFieldNamingPolicy(getConfig().getFieldNamingPolicy());

        try {
            getConfig().postProcessGsonBuilder(var0);
        } catch (Exception var2) {
            var2.printStackTrace();
        }

        return var0.create();
    }

    public static final <T> T getService(Context var0, Class<T> var1) {
        OkHttpClient var2 = getConfig().getOkHttpClient(var0);
        Retrofit.Builder var3 = (new Retrofit.Builder()).baseUrl(getConfig().getServerIpAddress()).client(var2);

        try {
            Iterator var4 = getConverterFactoryList(b).iterator();

            while(var4.hasNext()) {
                Converter.Factory var5 = (Converter.Factory)var4.next();
                var3.addConverterFactory(var5);
            }
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        var3.addConverterFactory(new NullOnEmptyConverterFactory()).addConverterFactory(GsonConverterFactory.create(getGson()));
        Retrofit var7 = var3.build();
        return var7.create(var1);
    }

    public static final List<Converter.Factory> getConverterFactoryList(Context var0) {
        return new ArrayList();
    }

    public static void bindImage(Context var0, ImageView var1, String var2, int var3) {
        checkPermission();

        try {
            Glide.with(var0).load(var2).error(var3).into(var1);
        } catch (Exception var5) {
            var5.printStackTrace();
        }

    }

    public static void bindImage(ImageView var0, String var1, int var2) {
        checkPermission();

        try {
            Glide.with(var0.getContext()).load(var1).error(var2).into(var0);
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }

    public static void bindImage(ImageView var0, String var1, int var2, int var3) {
        checkPermission();

        try {
            Glide.with(var0.getContext()).load(var1).error(var2).placeholder(var3).into(var0);
        } catch (Exception var5) {
            var5.printStackTrace();
        }

    }

    public static String formatDate(Date var0) {
        SimpleDateFormat var1 = new SimpleDateFormat(getConfig().getDateFormat());
        return var1.format(var0);
    }

    public static String formatTime(Date var0) {
        SimpleDateFormat var1 = new SimpleDateFormat(getConfig().getTimeFormat());
        return var1.format(var0);
    }

    public static String formatDateTime(Date var0) {
        SimpleDateFormat var1 = new SimpleDateFormat(getConfig().getDateTimeFormat());
        return var1.format(var0);
    }

    public static String parseTime(Date var0) {
        checkPermission();
        String var1 = "%dd ago";
        String var2 = "%dh ago";
        String var3 = "%dm ago";
        String var4 = "%ds ago";
        String var5 = "Just Now";
        Date var6 = new Date();
        long var7 = var6.getTime() - var0.getTime();
        long var9 = var7 / 3600000L;
        long var11 = var7 / 60000L % 60L;
        long var13 = var7 / 1000L % 60L;
        String var15 = var9 > 0L ? (var9 / 24L > 0L ? String.format(var1, (int)var9 / 24) : String.format(var2, (int)var9)) : (var11 > 0L ? String.format(var3, (int)var11) : (var13 > 0L ? String.format(var4, (int)var13) : var5));
        return var15;
    }

    public static String capitalizeString(String var0) {
        checkPermission();
        char[] var1 = var0.toLowerCase().toCharArray();
        boolean var2 = false;

        for(int var3 = 0; var3 < var1.length; ++var3) {
            if (!var2 && Character.isLetter(var1[var3])) {
                var1[var3] = Character.toUpperCase(var1[var3]);
                var2 = true;
            } else if (Character.isWhitespace(var1[var3]) || var1[var3] == '.' || var1[var3] == '\'') {
                var2 = false;
            }
        }

        return String.valueOf(var1);
    }

    public static void copyToClipboard(Context var0, String var1, String var2) {
        try {
            ClipboardManager var3 = (ClipboardManager)var0.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData var4 = ClipData.newPlainText(var1, var2);
            var3.setPrimaryClip(var4);
        } catch (Exception var5) {
            var5.printStackTrace();
        }

    }

    public static void copyToClipboard(Context var0, String var1) {
        try {
            ClipboardManager var2 = (ClipboardManager)var0.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData var3 = ClipData.newPlainText("", var1);
            var2.setPrimaryClip(var3);
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }

    public static Point getScreenSize(Context var0) {
        checkPermission();
        WindowManager var1 = (WindowManager)var0.getSystemService(Context.WINDOW_SERVICE);
        Display var2 = var1.getDefaultDisplay();
        Point var3 = new Point();

        try {
            var2.getSize(var3);
        } catch (NoSuchMethodError var5) {
            var3.x = var2.getWidth();
            var3.y = var2.getHeight();
        }

        return var3;
    }

    /*public static void shakeView(View var0) {
        YoYo.with(Techniques.Shake).duration((long)getConfig().getDefaultAnimationDuration()).playOn(var0);
    }*/

    public static void toast(Context var0, String var1) {
        checkPermission();

        try {
            e.cancel();
        } catch (NullPointerException var3) {
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        e = Toast.makeText(var0, var1, Toast.LENGTH_LONG);
        e.show();
    }

    public static void toast(Context var0, int var1) {
        checkPermission();

        try {
            e.cancel();
        } catch (NullPointerException var3) {
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        e = Toast.makeText(var0, var1, Toast.LENGTH_LONG);
        e.show();
    }

    public static boolean isOnline(Context var0) {
        ConnectivityManager var1 = (ConnectivityManager)var0.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo var2 = var1.getActiveNetworkInfo();
        return var2 != null && var2.isConnectedOrConnecting();
    }

    /*public static String MD5(String var0) {
        checkPermission();
        return new String(a.a.a.a.a.a.a(a.a.a.a.b.a.b(var0)));
    }

    public static String SHA1(String var0) {
        checkPermission();
        return new String(a.a.a.a.a.a.a(a.a.a.a.b.a.c(var0)));
    }*/

    public static void addFragment(FragmentManager var0, Fragment var1) {
        checkPermission();
        if (getConfig().getDefaultFragmentContainerResId() != -1) {
            var0.beginTransaction().replace(getConfig().getDefaultFragmentContainerResId(), var1).addToBackStack("main").commit();
        } else {
            log(HelperJav.class.getSimpleName() + ".addFragment", "Fragment Container Res ID Not found. Please implemented Config.getDefaultFragmentContainerResId() to use this function");
            throw new RuntimeException(HelperJav.class.getSimpleName() + ".addFragment | Fragment Container Res ID Not found. Please implemented Config.getDefaultFragmentContainerResId() to use this function");
        }
    }

    public static void changeFragment(FragmentManager var0, Fragment var1) {
        checkPermission();

        while(var0.popBackStackImmediate()) {
        }

        if (getConfig().getDefaultFragmentContainerResId() != -1) {
            var0.beginTransaction().replace(getConfig().getDefaultFragmentContainerResId(), var1).commit();
        } else {
            log(HelperJav.class.getSimpleName() + ".changeFragment", "Fragment Container Res ID Not found. Please implemented Config.getDefaultFragmentContainerResId() to use this function");
            throw new RuntimeException(HelperJav.class.getSimpleName() + ".addFragment | Fragment Container Res ID Not found. Please implemented Config.getDefaultFragmentContainerResId() to use this function");
        }
    }

    public static String getImei(Context var0) {
        checkPermission();
        String var1 = "";

        try {
            TelephonyManager var2 = (TelephonyManager)var0.getSystemService(Context.TELEPHONY_SERVICE);
            var1 = var2.getDeviceId();
        } catch (Exception var3) {
            var3.printStackTrace();
        }

        return var1;
    }

    public static String getAndroidId(Context var0) {
        checkPermission();
        String var1 = getImei(b);
        String var2 = "";

        try {
            var2 = Settings.Secure.getString(var0.getContentResolver(), "android_id");
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return String.format("%s = %s", var1, var2);
    }

    public static void log(Object var0, String var1) {
        checkPermission();

        try {
            String var2;
            if (var0 instanceof String) {
                var2 = (String)var0;
            } else {
                var2 = var0.getClass().getSimpleName();
            }

            if (var1 != null) {
                Log.v(var2, var1);
            } else {
                Log.v(var2, "Message is null!");
            }
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }

    public static String parseToApiDate(Date var0) {
        checkPermission();
        return (new SimpleDateFormat(getConfig().getApiDateFormat())).format(var0);
    }

    public static String formatCurrency(double var0) {
        checkPermission();
        return (new DecimalFormat(getConfig().getCurrencyFormat())).format(var0);
    }

    public static String formatCurrency(double var0, DecimalFormatSymbols var2) {
        checkPermission();
        return (new DecimalFormat(getConfig().getCurrencyFormat(), var2)).format(var0);
    }

    public static String formatDecimal(double var0) {
        checkPermission();
        return (new DecimalFormat(getConfig().getDecimalFormat())).format(var0);
    }

    public static String formatDecimal(double var0, DecimalFormatSymbols var2) {
        checkPermission();
        return (new DecimalFormat(getConfig().getDecimalFormat(), var2)).format(var0);
    }

    public static boolean checkPermission(Context var0, String var1) {
        if (Build.VERSION.SDK_INT < 23) {
            return true;
        } else {
            int var2 = ContextCompat.checkSelfPermission(var0, var1);
            return var2 == 0;
        }
    }

    /*public static boolean checkPermission(final Context var0, String var1, boolean var2) {
        if (checkPermission(var0, var1)) {
            return true;
        } else {
            try {
                if (var2) {
                    (new CustomConfirmationDialog(var0, string.dialog_request_permission_title, String.format(var0.getString(string.dialog_request_permission_content), var1), string.dialog_request_permission_positive_text, string.dialog_request_permission_negative_text, new ButtonCallback() {
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

    public static void hideSoftKeyboard(Activity var0) {
        try {
            InputMethodManager var1 = (InputMethodManager)var0.getSystemService(Context.INPUT_METHOD_SERVICE);
            var1.hideSoftInputFromWindow(var0.getCurrentFocus().getWindowToken(), 2);
        } catch (Exception var2) {
            var2.printStackTrace();
        }

    }

    static {
        a = CustomConfig.INSTANCE.getPACKAGE_NAMES();
        b = null;
        c = null;
        d = false;
        dbHelper = null;
    }
}
