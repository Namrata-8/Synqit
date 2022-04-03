package com.example.synqit.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.synqit.ui.dashboard.model.CardData;
import com.example.synqit.ui.login.model.LoginData;
import com.example.synqit.ui.proupgrade.model.FullRegisterData;
import com.example.synqit.ui.register.model.BasicRegisterResponse;
import com.example.synqit.ui.register.model.RegisterData;
import com.google.gson.Gson;

public class SessionManager {
    SharedPreferences userPreferences;
    SharedPreferences.Editor mEditor;
    Context mContext;

    public SessionManager(Context _context, String sessionName) {
        mContext = _context;
        userPreferences = mContext.getSharedPreferences(sessionName, Context.MODE_PRIVATE);
        mEditor = userPreferences.edit();
    }

    public static final String PREF_NAME = "USER_PREF";
    public static final int MODE = Context.MODE_PRIVATE;

    public static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(PREF_NAME, MODE);
    }

    public static SharedPreferences.Editor getEditor(Context context) {
        return getPreferences(context).edit();
    }

    /* Basic Registration */
    public static final String BR_basicRegistratinUID = "BR_basicRegistratinUID";
    public static final String BR_BasicRegisterData = "BR_BasicRegisterData";

    public static void saveBasicRegisterData(Context context, String key, RegisterData basicRegisterData){
        getEditor(context).putString(key, new Gson().toJson(basicRegisterData)).commit();
    }

    public static RegisterData readBasicRegisterData(Context context, String key, String defValue){
        String basicRegisterData = getPreferences(context).getString(key, defValue);
        if (!basicRegisterData.isEmpty()){
            return new Gson().fromJson(basicRegisterData, RegisterData.class);
        }
        return null;
    }

    /* Full Registration */
    public static final String FR_userID = "FR_userID";
    public static final String FR_FullRegisterData = "FR_FullRegisterData";

    public static void saveFullRegisterData(Context context, String key, FullRegisterData fullRegisterData){
        getEditor(context).putString(key, new Gson().toJson(fullRegisterData)).commit();
    }

    public static FullRegisterData readFullRegisterData(Context context, String key, String defValue){
        String fullRegisterData = getPreferences(context).getString(key, defValue);
        if (!fullRegisterData.isEmpty()){
            return new Gson().fromJson(fullRegisterData, FullRegisterData.class);
        }
        return null;
    }

    /* Login */
    public static final String L_userUID = "L_userUID";
    public static final String parentUserID = "parentUserID";
    public static final String L_LoginData = "L_LoginData";

    public static void saveLoginData(Context context, String key, LoginData loginData){
        getEditor(context).putString(key, new Gson().toJson(loginData)).commit();
    }

    public static LoginData readLoginData(Context context, String key, String defValue){
        String loginData = getPreferences(context).getString(key, defValue);
        if (!loginData.isEmpty()){
            return new Gson().fromJson(loginData, LoginData.class);
        }
        return null;
    }

    /* GetOTP */
    public static final String GO_verificationId = "GO_verificationId";

    /* Social Login */
    public static final String SL_IsLogin = "SL_IsLogin";
    public static final String SL_LoginData = "SL_LoginData";

    public static void saveSocialLoginData(Context context, String key, FullRegisterData socialLoginData){
        getEditor(context).putString(key, new Gson().toJson(socialLoginData)).commit();
    }

    public static FullRegisterData readSocialLoginData(Context context, String key, String defValue){
        String socialLoginData = getPreferences(context).getString(key, defValue);
        if (!socialLoginData.isEmpty()){
            return new Gson().fromJson(socialLoginData, FullRegisterData.class);
        }
        return null;
    }

    /* Selected Card */
    public static final String Selected_Card_Data = "Selected_Card_Data";
    public static void saveSelectedCardData(Context context, String key, String value){
        getEditor(context).putString(key, value).commit();
    }

    public static String readSelectedCardData(Context context, String key, String defValue){
            return getPreferences(context).getString(key, defValue);
    }

    public static final String BASIC_REGISTER_WITH = "basic_register_with";

    public static final String REGISTER_AS = "register_as";
    public static final String SELECTED_BUSINESSES_IDS = "selected_business_ids";
    public static final String COMPANY_NAME = "company_name";
    public static final String FULL_NAME = "full_name";
    public static final String COUNTRY_NAME = "country_name";
    public static final String LOGO_IMAGE_PATH = "logo_img_path";
    public static final String COVER_IMAGE_PATH = "cover_img_path";
    public static final String LOGO_FILE_URL = "logo_file_url";
    public static final String COVER_FILE_URL = "cover_file_url";

    public static final String Selected_logo_url = "Selected_logo_url";
    public static final String Selected_cover_url = "Selected_cover_url";
    public static final String Selected_name = "Selected_name";
    public static final String Selected_userId = "Selected_userId";

    public static final String DOB = "dob";
    public static final String CITY = "city";
    public static final String GENDER = "gender";

    public static final String IS_LIGHT_DARK = "is_light_dark";
    public static final String SELECTED_CARD_POSITION = "selected_card_position";

    public static void writeString(Context context, String key, String value) {
        getEditor(context).putString(key, value).commit();
    }

    public static void writeInt(Context context, String key, int value) {
        getEditor(context).putInt(key, value).commit();

    }

    public static void writeBoolean(Context context, String key, boolean value) {
        getEditor(context).putBoolean(key, value).commit();

    }

    public static String readString(Context context, String key, String defValue) {
        return getPreferences(context).getString(key, defValue);
    }

    public static int readInt(Context context, String key, int defValue) {
        return getPreferences(context).getInt(key, defValue);
    }

    public static boolean readBoolean(Context context, String key, boolean defValue) {
        return getPreferences(context).getBoolean(key, defValue);
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

    public static void saveOnBoardingView(Context context, boolean status){
        SharedPreferences preferences = context.getSharedPreferences("ON_BOARDING", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("InFirstTime", status);
        editor.apply();
    }

    public static boolean getOnBoardingView(Context context){
        SharedPreferences preferences = context.getSharedPreferences("ON_BOARDING", Context.MODE_PRIVATE);
        return preferences.getBoolean("InFirstTime", true);
    }

    public static void clearOnBoardingView(Context context){
        SharedPreferences preferences = context.getSharedPreferences("ON_BOARDING", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }

}
