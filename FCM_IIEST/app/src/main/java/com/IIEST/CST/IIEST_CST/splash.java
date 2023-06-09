package com.IIEST.CST.IIEST_CST;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

/**
 * Created by CST on 28/1/17.
 */
public class splash extends Activity {
    private static int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(ApplicationStatus.splash_screen_displayed){
            Intent i = new Intent(splash.this, MainActivity.class);
            startActivity(i);
            finish();
        }
        else{
            setContentView(R.layout.splash);
            new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case ymainour app logo / company
             */

                @Override
                public void run() {
                    // This method will be executed once the timer is over
                    // Start your app main activity
                    if(!alreadyLoggedIn()) {
                        Intent intent = new Intent(splash.this, LoginActivity.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(splash.this, MainActivity.class);
                        startActivity(intent);
                    }


                    // close this activity
                    finish();
                }
            }, SPLASH_TIME_OUT);
        }
    }

    private boolean alreadyLoggedIn() {
        String[] res;
        My_SQLite sqlite = new My_SQLite(getApplicationContext());
        try {
            res = sqlite.read();
            Log.e("Success => ","Hurray => "+res[0]+" status => "+res[2]);
            return res[2].equals("Y");
        } catch (Exception e) {
            Log.e("Error => ","failure");
            if (sqlite == null) {
                return false;
            }
            return false;
        }
    }
}
