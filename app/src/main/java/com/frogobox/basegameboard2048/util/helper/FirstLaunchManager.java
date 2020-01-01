package com.frogobox.basegameboard2048.util.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.frogobox.basegameboard2048.source.local.db.PFASQLiteHelper;

import static com.frogobox.basegameboard2048.util.helper.ConstHelper.Pref.IS_FIRST_TIME_LAUNCH;
import static com.frogobox.basegameboard2048.util.helper.ConstHelper.Pref.PREF_NAME;
import static com.frogobox.basegameboard2048.util.helper.ConstHelper.Pref.PRIVATE_MODE;

public class FirstLaunchManager {
    private PFASQLiteHelper dbHandler;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    public FirstLaunchManager(Context context) {
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        dbHandler = new PFASQLiteHelper(context);
        editor = pref.edit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public void initFirstTimeLaunch() {
        if (pref.getBoolean(IS_FIRST_TIME_LAUNCH, true)) {
            // First time setup in here
        }
    }

}
