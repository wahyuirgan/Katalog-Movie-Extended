package top.technopedia.myapplicationkatalogfilm;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;


public class SettingPreference {
    private String KEY_UPCOMING = "upcoming";
    private String KEY_DAILY = "daily";

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    public SettingPreference(Context context) {
        String PREF_NAME = "UserPref";
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void setUpcoming(boolean status){
        editor.putBoolean(KEY_UPCOMING, status);
        editor.apply();
    }

    public void setDaily(boolean status){
        editor.putBoolean(KEY_DAILY, status);
        editor.apply();
    }

    public boolean isUpcoming(){
        return preferences.getBoolean(KEY_UPCOMING,false);
    }

    public boolean isDaily(){
        return preferences.getBoolean(KEY_DAILY, false);
    }
}
