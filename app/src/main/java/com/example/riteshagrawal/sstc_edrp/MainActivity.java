package com.example.riteshagrawal.sstc_edrp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;


import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

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
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        sd = this.getSharedPreferences("com.example.riteshagrawal.sstc_edrp", Context.MODE_APPEND);
        id = (AutoCompleteTextView) findViewById(R.id.id);
        appContext=getApplicationContext();

        id.requestFocus();
        pass = (EditText) findViewById(R.id.pass);

        Gson gson = new Gson();

        if(!sd.getString("Users_Data_Saver", "").equals("")) {
            String json1 = sd.getString("Users_Data_Saver", "");
            System.out.println("here is json 1" + json1);
            UserDataSaverObject obj1 = gson.fromJson(json1, UserDataSaverObject.class);
            countryList = obj1.getList();
        }

        id.postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.showSoftInput(id, InputMethodManager.SHOW_IMPLICIT);
            }
        }, 500);

        obj = new ObjectAdapter(getApplicationContext(),
                countryList);

        id.setAdapter(obj);
        id.setThreshold(1);

        id.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                id.setText(countryList.get(i).getUname());
                pass.setText(countryList.get(i).getPass());

            }
        });






        sd.edit().putString("lastcall", "0").apply();





        if(getIntent().hasExtra("error_msg")){
            System.out.println("yehh it error id pass is working ........");
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
            Intent i = new Intent(MainActivity.this, attend_shower.class);
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
            Intent i = new Intent(MainActivity.this, attend_shower.class);
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
                String shareBodyText = "http://play.google.com/store/apps/details?id=com.SahuAppsPvtLtd.myTrainEnquiryApp";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Subject here");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
                startActivity(Intent.createChooser(sharingIntent, "Share This App"));

                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "2");
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Share app");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
                return true;

            case R.id.rate:
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://play.google.com/store/apps/details?id=com.SahuAppsPvtLtd.myTrainEnquiryApp"));
                startActivity(intent);
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "3");
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "rate app");
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


}
