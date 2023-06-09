package com.IIEST.CST.IIEST_CST;

import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by alokedip on 3/5/17.
 */

public class KeyRegistration extends AsyncTask<Void,Void,Boolean> {
    Boolean flag = true;
    Context context;
    String key,e_mail;
    StringBuilder sb = null;
    private SharedPreferences sharedPreferences;
    private int backoff = 100;
    private int count = 0;
    KeyRegistration(Context context){
        Log.e("Key reg => ","contructor");
        this.context=context;
        sharedPreferences = context.getSharedPreferences(URL_Strings.shared_pref,Context.MODE_PRIVATE);
        key = sharedPreferences.getString(URL_Strings.unique_ID,null);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(URL_Strings.IsRegistered,false);
        editor.putString(URL_Strings.unique_ID,"");
        editor.commit();
        My_SQLite my_sqLite = new My_SQLite(context);
        String[] res = my_sqLite.read();
        e_mail = res[0];
    }
    @Override
    protected Boolean doInBackground(Void... voids) {
        while(flag){
            try{
                String data  = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(key, "UTF-8");
                data += "&" + URLEncoder.encode("e_mail", "UTF-8") + "=" + URLEncoder.encode(e_mail, "UTF-8");
                URL url = new URL(URL_Strings.registration_link);
                URLConnection reg_conn = url.openConnection();
                reg_conn.setDoOutput(true);
                OutputStreamWriter post_send = new OutputStreamWriter(reg_conn.getOutputStream());
                post_send.write(data);
                post_send.flush();
                BufferedReader server_response = new BufferedReader(new InputStreamReader(reg_conn.getInputStream()));
                sb = new StringBuilder();
                String line;

                // Read Server Response
                while((line = server_response.readLine()) != null) {
                    sb.append(line);
                    break;
                }
                Log.e("Key Reg => ","Last Instruction in try.."+sb.toString().trim());
                if(sb.toString().trim().equalsIgnoreCase("1")){
                    flag = !flag;
                }
            }catch (Exception e){
                if(count > backoff){
                    return false;
                }
                count++;
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                Log.e("Error in key Reg => ", String.valueOf(e));
            }
        }
        return true;
    }
    @Override
    protected void onPostExecute(final Boolean success) {
        if(success){
            send_notification("thank you for providing E-mail address");
        }
        else{
            send_notification("Server Error. Try to install the app again if you will not be able to get notification. Sorry for inconvenience");
        }
    }

    private void send_notification(String messageBody) {
        Uri SoundUri=Uri.parse("android.resource://" + context.getPackageName() + "/" +R.raw.notify);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.institute)
                .setContentTitle(messageBody)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(SoundUri);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }
}
