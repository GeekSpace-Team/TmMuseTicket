package com.android.ticketadmin.Common;

import static android.content.Context.MODE_PRIVATE;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.util.DisplayMetrics;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Utils {

    public static void setSharedPreference(Context context, String name, String value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(name, MODE_PRIVATE).edit();
        editor.putString(name, value);
        editor.apply();
    }

    public static String getSharedPreference(Context context, String name) {
        SharedPreferences prefs = context.getSharedPreferences(name, MODE_PRIVATE);
        String value = prefs.getString(name, "");
        return value;
    }

    public static ProgressDialog progressDialog(Context context){
        ProgressDialog progressDialog=new ProgressDialog(context);
        progressDialog.setCancelable(true);
        progressDialog.setTitle("Biraz garashyn...");
        return progressDialog;
    }

    public static int calculateNoOfColumns(Context context, float columnWidthDp) { // For example columnWidthdp=180
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float screenWidthDp = displayMetrics.widthPixels / displayMetrics.density;
        return (int) (screenWidthDp / columnWidthDp + 0.5);
    }

    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    public static String covertStringToDate(String date) throws ParseException {
        String response = "";
        Date newDate= null;
        if (date != null) {
            SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.ss",Locale.US);
            spf.setTimeZone(TimeZone.getDefault());
            newDate = spf.parse(date);
            spf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
            response = spf.format(newDate);
            return response;
        } else{
            return "";
        }
    }
}
