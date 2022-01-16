package com.example.synqit.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    SharedPreferences userPreferences;
    SharedPreferences.Editor mEditor;
    Context mContext;

    public static final String SESSION_USERSESSION = "userLoginSession";
    public static final String PREF_NAME = "USER_PREF";
    public static final int MODE = Context.MODE_PRIVATE;

    public static final String IS_BASIC_REGISTER = "is_basic_register";
    public static final String BASIC_REGISTER_WITH = "basic_register_with";
    public static final String BASIC_REGISTER_ID = "basic_register_id";
    public static final String BASIC_REGISTER_EMAIL = "basic_register_email";
    public static final String BASIC_REGISTER_MOBILE = "basic_register_mobile";
    public static final String BASIC_REGISTER_PASSWORD = "basic_register_password";

    public static final String OTP_VERIFICATION_ID = "otp_verification_id";

    public static final String REGISTER_AS = "register_as";
    public static final String SELECTED_BUSINESSES_IDS = "selected_business_ids";
    public static final String COMPANY_NAME = "company_name";
    public static final String FULL_NAME = "full_name";
    public static final String COUNTRY_NAME = "country_name";
    public static final String LOGO_IMAGE_PATH = "logo_img_path";
    public static final String COVER_IMAGE_PATH = "cover_img_path";
    public static final String LOGO_FILE_URL = "logo_file_url";
    public static final String COVER_FILE_URL = "cover_file_url";

    public static final String USER_UID = "user_uid";
    public static final String IS_BUSINESS = "is_business";
    public static final String IS_PRIVATE = "is_private";
    public static final String IS_FULL_REGISTERED = "is_full_registered";
    public static final String DOB = "dob";
    public static final String GENDER = "gender";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String DISPLAY_NAME = "display_name";
    public static final String BUSINESS_TYPE = "business_type";
    public static final String PLAN = "plan";
    public static final String MOBILE_NO = "mobile_no";
    public static final String Is_BASIC_LOGIN = "is_basic_login";

    public static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(PREF_NAME, MODE);
    }

    public static void writeString(Context context, String key, String value) {
        getEditor(context).putString(key, value).commit();

    }

    public static void writeBoolean(Context context, String key, boolean value) {
        getEditor(context).putBoolean(key, value).commit();

    }

    public static String readString(Context context, String key, String defValue) {
        return getPreferences(context).getString(key, defValue);
    }

    public static boolean readBoolean(Context context, String key, boolean defValue) {
        return getPreferences(context).getBoolean(key, defValue);
    }

    public static SharedPreferences.Editor getEditor(Context context) {
        return getPreferences(context).edit();
    }

    public static void removeValue(Context context, String key){
        getEditor(context).remove(key);
    }

    /*Auto Login Session*/
    public static void saveAutoLogin(Context context, boolean status){
        SharedPreferences preferences = context.getSharedPreferences("AUTOLOGIN", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("IsAutoLogin", status);
        editor.apply();
    }

    public static boolean getAutoLogin(Context context){
        SharedPreferences preferences = context.getSharedPreferences("AUTOLOGIN", Context.MODE_PRIVATE);
        return preferences.getBoolean("IsAutoLogin", false);
    }

    public static void clearAutoLogin(Context context){
        SharedPreferences preferences = context.getSharedPreferences("AUTOLOGIN", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }
}
