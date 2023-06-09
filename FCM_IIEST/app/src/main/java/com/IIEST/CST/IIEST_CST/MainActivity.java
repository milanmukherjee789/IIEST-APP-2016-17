package com.IIEST.CST.IIEST_CST;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private WebView webView;
    private ProgressDialog mProgressDialog;
    private Context context;
    private String data="";
    //@SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ApplicationStatus.splash_screen_displayed=true;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        context=this;
        //setProgressDialog(this);
        webView=(WebView)findViewById(R.id.webview);
    }
    @Override
    public void onResume(){
        super.onResume();
        Boolean f = deviceregistered();
        Log.e("on resume => ", String.valueOf(f));
        if(f){
            KeyRegistration server_channel = new KeyRegistration(this);
            server_channel.execute((Void) null);
        }
        data=getIntent().getDataString();
        if(data==null){
            set_webView(URL_Strings.home);
        }
        else{
            set_webView(data);
        }
        //Toast.makeText(this,"Incoming data  "+data,Toast.LENGTH_SHORT).show();
    }

    private boolean deviceregistered() {
        SharedPreferences share = getSharedPreferences(URL_Strings.shared_pref,MODE_PRIVATE);
        return share.getBoolean(URL_Strings.IsRegistered,false);
    }

    private void set_webView(String url) {
        //setProgressDialog(context);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url)
            {
                webView.loadUrl("javascript:(function() { " +
                        "document.getElementsByClassName('container')[0].style.display='none'; document.getElementsByClassName('custom visit-old-site')[0].style.display='none';" +
                        "})()");
                //mProgressDialog.dismiss();
            }
            @SuppressWarnings("deprecation")
            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {

                // TODO Auto-generated method stub
                super.onReceivedError(view, errorCode, description, failingUrl);

                /*webView
                        .loadData(
                                "<div>Please check your internet connection.</div>",
                                "text/html", "UTF-8");*/
                webView.loadUrl("file:///android_asset/error.html");

            }

            @TargetApi(android.os.Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr) {
                // Redirect to deprecated method, so you can use it in all SDK versions
                onReceivedError(view, rerr.getErrorCode(), rerr.getDescription().toString(), req.getUrl().toString());

            }
        });
        webView.setDownloadListener(new DownloadListener() {
            public void onDownloadStart(String url, String userAgent,
                                        String contentDisposition, String mimetype,
                                        long contentLength) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.loadUrl(url);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        Log.e("WEBVIEW URL : ",webView.getUrl());
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id==R.id.Student){
            set_webView(URL_Strings.student_notice);
            return true;
        }
        if (id==R.id.Faculty){
            set_webView(URL_Strings.faculty_notice);
            return true;
        }
        if (id==R.id.Admission){
            set_webView(URL_Strings.admission_notice);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home) {
            if(!webView.getUrl().equalsIgnoreCase(URL_Strings.home)) {
                set_webView(URL_Strings.home);
            }
        } else if (id == R.id.institute) {
            if(!webView.getUrl().equalsIgnoreCase(URL_Strings.Institute))
                set_webView(URL_Strings.Institute);

        } else if (id == R.id.academics) {
            if(!webView.getUrl().equalsIgnoreCase(URL_Strings.Academics))
                set_webView(URL_Strings.Academics);

        } else if (id == R.id.admission) {
            if(!webView.getUrl().equalsIgnoreCase(URL_Strings.Admission))
                set_webView(URL_Strings.Admission);

        } else if (id == R.id.industry) {
            if (!webView.getUrl().equalsIgnoreCase(URL_Strings.Industry))
                set_webView(URL_Strings.Industry);

        } else if (id == R.id.research) {
            if (!webView.getUrl().equalsIgnoreCase(URL_Strings.Research))
                set_webView(URL_Strings.Research);
        }
        else if(id==R.id.nav_share){
            //share_bluetooth();
            send_apk();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void send_apk() {
        try{
            ArrayList<Uri> uris = new ArrayList<>();
            Intent sendIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
            sendIntent.setType("application/*");
            uris.add(Uri.fromFile(new File(getApplicationInfo().publicSourceDir)));
            sendIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);
            startActivity(Intent.createChooser(sendIntent, null));

        }catch(Exception e){}
    }
    /*private void setProgressDialog(Context context) {
        mProgressDialog=new ProgressDialog(context);
        mProgressDialog.setMessage("Please Wait .......");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                if (mProgressDialog!= null) {
                    if (mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                }
            }
        };
        Handler progress_cancel=new Handler();
        progress_cancel.postDelayed(runnable,10000);
    }*/
    @Override
    public void onDestroy(){
        super.onDestroy();
        if(mProgressDialog!=null && mProgressDialog.isShowing()){
            mProgressDialog.dismiss();
        }
        ApplicationStatus.splash_screen_displayed=false;
    }
}
