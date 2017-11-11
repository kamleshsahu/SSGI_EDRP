package com.example.riteshagrawal.sstc_edrp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements ForceUpdateChecker.OnUpdateNeededListener{
    private static final String TAG = MainActivity.class.getSimpleName();
   static SharedPreferences sd;
    String LoginParams="";
    AutoCompleteTextView id;
    EditText pass;
    static Context appContext;
   static FirebaseAnalytics mFirebaseAnalytics;
   static Bundle bundle = new Bundle();
    private ArrayList<Users_info_Object> countryList= new ArrayList<>();
    private ObjectAdapter obj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final FirebaseRemoteConfig firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();

        // set in-app defaults
        Map<String, Object> remoteConfigDefaults = new HashMap();
        remoteConfigDefaults.put(ForceUpdateChecker.KEY_UPDATE_REQUIRED, false);
        remoteConfigDefaults.put(ForceUpdateChecker.KEY_CURRENT_VERSION, "1.8");
        remoteConfigDefaults.put(ForceUpdateChecker.KEY_UPDATE_URL,
                "https://play.google.com/store/apps/details?id=com.SSTC.EDRP");

        firebaseRemoteConfig.setDefaults(remoteConfigDefaults);
        firebaseRemoteConfig.fetch(60) // fetch every minutes
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "remote config is fetched.");
                            firebaseRemoteConfig.activateFetched();
                        }
                    }
                });


        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        ForceUpdateChecker.with(this).onUpdateNeeded(this).check();

        sd = this.getSharedPreferences("com.example.riteshagrawal.sstc_edrp", Context.MODE_APPEND);
        id = (AutoCompleteTextView) findViewById(R.id.id);
        appContext=getApplicationContext();

        id.requestFocus();
        pass = (EditText) findViewById(R.id.pass);


        id.postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.showSoftInput(id, InputMethodManager.SHOW_IMPLICIT);
            }
        }, 500);


        sd.edit().putString("lastcall", "0").apply();
        if(getIntent().hasExtra("error_msg")){
            ////System.out.println("yehh it error id pass is working ........");
            Toast.makeText(MainActivity.this,getIntent().getStringExtra("error_msg"),Toast.LENGTH_LONG).show();
            sd.edit().putString("loginParams", "").apply();
            sd.edit().putString("c_uname", "").apply();
            sd.edit().putString("c_pass", "").apply();
        }
        else if(sd.getString("loginParams","").equals("")) {
            sd.edit().putString("loginParams", "").apply();
            sd.edit().putString("c_uname", "").apply();
            sd.edit().putString("c_pass", "").apply();
        }else {
           // LoginParams="uname="+"BE20160467"+"&"+"password="+"9644790733"+"&cmbsession=JUL-17";
            //sd.edit().putString("loginParams", LoginParams).apply();
            Intent i = new Intent(MainActivity.this, AttendenceActivity.class);
            startActivity(i);
        }
       // sd.edit().putString("uname=0201160139&password=9981140217&cmbsession=JUL-17")



    }

    public void LoginOnClick(View view) {
        if(!id.getText().equals("") && !pass.getText().equals("")){
            LoginParams="uname="+id.getText().toString().toUpperCase()+"&"+"password="+pass.getText()+"&cmbsession=JUL-17";
            sd.edit().putString("c_uname", id.getText().toString().toUpperCase()).apply();
            sd.edit().putString("c_pass", pass.getText().toString()).apply();
            sd.edit().putString("loginParams", LoginParams).apply();
            Intent i = new Intent(MainActivity.this, AttendenceActivity.class);
            startActivity(i);
            MainActivity.this.finish();

            bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "1");
            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Login Activity");
            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
        }else{
            Toast.makeText(MainActivity.this,"please fill id and pass ",Toast.LENGTH_LONG).show();
            sd.edit().putString("loginParams", "").apply();
            sd.edit().putString("c_uname", "").apply();
            sd.edit().putString("c_pass", "").apply();
        }
    }

    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.share, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Bundle bundle = new Bundle();
        switch (item.getItemId()) {
            case R.id.share:
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBodyText = "https://play.google.com/store/apps/details?id=com.SSTC.EDRP";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Subject here");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
                startActivity(Intent.createChooser(sharingIntent, "Share This App"));

                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "2");
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Share app");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
                return true;

            case R.id.rate:
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.SSTC.EDRP"));
                startActivity(intent);
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "3");
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "rate app");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
                return true;
            case R.id.reportbug:
                String msg="hii developers at SSTC EDRP ,Pls Checkout the error\n(pls send the screenshots manually)";
                Intent sendIntent = new Intent("android.intent.action.MAIN");
                sendIntent.putExtra("jid", "919644790733@s.whatsapp.net");
                sendIntent.putExtra(Intent.EXTRA_TEXT,msg);
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.setPackage("com.whatsapp");
                sendIntent.setType("text/plain");
                PackageManager packageManager = getPackageManager();
                if (sendIntent.resolveActivity(packageManager) != null) {
                    startActivity(sendIntent);
                } else {
                    Toast.makeText(getApplicationContext(), "Cannot handle this intent",Toast.LENGTH_LONG).show();
                }
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
                return true;
            case R.id.about_us:
                Intent o=new Intent(MainActivity.this,about_us.class);
                startActivity(o);
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "3");
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "about us");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onUpdateNeeded(final String updateUrl) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("New version available")
                .setMessage("Please, update app to the latest version.")
                .setPositiveButton("Update",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                redirectStore(updateUrl);
                            }
                        }).setNegativeButton("No, thanks",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                   // finish();
                                    dialog.dismiss();
                                }catch (Exception e){
                                    e.fillInStackTrace();
                                    System.out.println("error in the no thanks ,dont update click...");
                                }
                            }
                        }).create();
        dialog.show();
    }

    private void redirectStore(String updateUrl) {
        final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(updateUrl));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
