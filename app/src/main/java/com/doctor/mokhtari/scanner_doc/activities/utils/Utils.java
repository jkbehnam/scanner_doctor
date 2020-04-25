package com.doctor.mokhtari.scanner_doc.activities.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;


import com.doctor.mokhtari.scanner_doc.R;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import saman.zamani.persiandate.PersianDate;

import static android.content.pm.PackageManager.GET_META_DATA;
import static android.os.Build.VERSION_CODES.P;

/**
 * Created by cstudioo on 06/01/17.
 */

public class Utils {

    /**
     * check email validations.
     *
     * @param target
     * @return
     */
    public static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }


    /**
     * Method is used for checking network availability.
     *
     * @param context
     * @return isNetAvailable: boolean true for Internet availability, false
     * otherwise
     */
    public static boolean isNetworkAvailable(Context context) {

        boolean isNetAvailable = false;
        if (context != null) {

            final ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            if (mConnectivityManager != null) {

                boolean mobileNetwork = false;
                boolean wifiNetwork = false;

                boolean mobileNetworkConnected = false;
                boolean wifiNetworkConnected = false;

                final NetworkInfo mobileInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                final NetworkInfo wifiInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

                if (mobileInfo != null) {

                    mobileNetwork = mobileInfo.isAvailable();
                }

                if (wifiInfo != null) {

                    wifiNetwork = wifiInfo.isAvailable();
                }

                if (wifiNetwork || mobileNetwork) {

                    if (mobileInfo != null) {

                        mobileNetworkConnected = mobileInfo.isConnectedOrConnecting();
                    }
                    wifiNetworkConnected = wifiInfo.isConnectedOrConnecting();
                }
                isNetAvailable = (mobileNetworkConnected || wifiNetworkConnected);
            }
        }

        return isNetAvailable;
    }


    /**
     * Common AppCompat Alert Dialog to be used in the Application everywhere
     *
     * @param mContext, Context of where to display
     */
    public static void displayCommonAlertDialog(final Context mContext, final String alertMessage) {
        try {
            androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(mContext, R.style.StyleAppCompatAlertDialog);
            builder.setTitle(mContext.getResources().getString(R.string.app_name));
            builder.setMessage(alertMessage);
            builder.setPositiveButton("باشه", null);
            builder.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static String hexString(Resources res) {
        Object resImpl = getPrivateField("android.content.res.Resources", "mResourcesImpl", res);
        Object o = resImpl != null ? resImpl : res;
        return "@" + Integer.toHexString(o.hashCode());
    }

    public static Object getPrivateField(String className, String fieldName, Object object) {
        try {
            Class c = Class.forName(className);
            Field f = c.getDeclaredField(fieldName);
            f.setAccessible(true);
            return f.get(object);
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void resetActivityTitle(Activity a) {
        try {
            ActivityInfo info = a.getPackageManager().getActivityInfo(a.getComponentName(), GET_META_DATA);
            if (info.labelRes != 0) {
                a.setTitle(info.labelRes);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static String getRequestState(String s){
        switch (s){
            case "progress":
                return "در انتظار بررسی";
            case "answerd":
                return "بررسی شده";
            case "endchat":
                return "پایان یافته";
        }
        return "";
    }
    public static String getRequestQues(String s){
        switch (s){
            case "yes":
                return "بله";
            case "no":
                return "خیر";
        }
        return "";
    }
 public static String getPersianDate(String str_date){
     DateFormat formatter;
     Date date = null;
     formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
     try {
         date = formatter.parse(str_date);
     } catch (ParseException e) {
         e.printStackTrace();
     }
     PersianDate pd=new PersianDate(date);
     return pd.getShYear()+"/"+pd.getShMonth()+"/"+pd.getShDay();
 }
    public static Long getTimeStamp(String str_date){
        DateFormat formatter;
        Date date = null;
        formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            date = formatter.parse(str_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date.getTime();
    }
    @SuppressWarnings("unchecked")
    public static String getTitleCache() {
        // https://developer.android.com/about/versions/pie/restrictions-non-sdk-interfaces
        if (isAtLeastVersion(P)) return "Can't access title cache\nstarting from API 28";
        Object o = Utils.getPrivateField("android.app.ApplicationPackageManager", "sStringCache", null);
        Map<?, WeakReference<CharSequence>> cache = (Map<?, WeakReference<CharSequence>>) o;
        if (cache == null) return "";

        StringBuilder builder = new StringBuilder("Cache:").append("\n");
        for (Map.Entry<?, WeakReference<CharSequence>> e : cache.entrySet()) {
            CharSequence title = e.getValue().get();
            if (title != null) {
                builder.append(title).append("\n");
            }
        }
        return builder.toString();
    }

    public static Resources getTopLevelResources(Activity a) {
        try {
            return a.getPackageManager().getResourcesForApplication(a.getApplicationInfo());
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isAtLeastVersion(int version) {
        return Build.VERSION.SDK_INT >= version;
    }
}
