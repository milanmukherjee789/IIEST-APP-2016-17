package com.IIEST.CST.IIEST_CST;

import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by alokedip on 30/4/17.
 */

public class Firebase_registration extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        String refreshed_token = FirebaseInstanceId.getInstance().getToken();
        SharedPreferences sharedPreferences = getSharedPreferences(URL_Strings.shared_pref,MODE_PRIVATE);
        Log.d("Registration token => ", refreshed_token);
        URL_Strings.FCM_registration_token = refreshed_token;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(URL_Strings.unique_ID,refreshed_token);
        editor.putBoolean(URL_Strings.IsRegistered,true);
        editor.commit();
    }
}
