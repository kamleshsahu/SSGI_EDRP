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



import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

   static SharedPreferences sd;
    String LoginParams="";
    AutoCompleteTextView id;
    EditText pass;
    static Context appContext;


    private ArrayList<Users_info_Object> countryList= new ArrayList<>();
    private ObjectAdapter obj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         sd=MainActivity.sd;
        id = (AutoCompleteTextView) findViewById(R.id.id);
        appContext=getApplicationContext();

   //     id = (EditText) findViewById(R.id.id);
        id.requestFocus();
        pass = (EditText) findViewById(R.id.pass);
       countryList.add(new Users_info_Object("BE20160467","9644790733","kamlesh "));
        countryList.add(new Users_info_Object("BE20160826","9617340924","somnath "));
        countryList.add(new Users_info_Object("BE20170287","9982313173","asif"));
        countryList.add(new Users_info_Object("BE20160236","9165150404","puspraj"));
        countryList.add(new Users_info_Object("0201150185","9977645873","shivam "));
        countryList.add(new Users_info_Object("0201150185","9977645873","vashudha "));
        countryList.add(new Users_info_Object("BE20160706","8120822235","mukesh "));



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
//                pass.requestFocus();
//
//
//                pass.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                        inputMethodManager.showSoftInput(id, InputMethodManager.SHOW_IMPLICIT);
//                    }
//                }, 200);
            }
        });


        sd = this.getSharedPreferences("com.example.riteshagrawal.sstc_edrp", Context.MODE_APPEND);


        //System.out.println("sd lastcall is empty!!!");
        sd.edit().putString("lastcall", "0").apply();




        System.out.println("hii by");

        if(getIntent().hasExtra("error_msg")){
            System.out.println("yehh it error id pass is working ........");
            Toast.makeText(MainActivity.this,getIntent().getStringExtra("error_msg"),Toast.LENGTH_LONG).show();
        }
        else if(sd.getString("loginParams","").equals("")) {
            sd.edit().putString("loginParams", "").apply();

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
        }else{
            Toast.makeText(MainActivity.this,"please fill id and pass ",Toast.LENGTH_LONG).show();
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
                return true;

            case R.id.rate:
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://play.google.com/store/apps/details?id=com.SahuAppsPvtLtd.myTrainEnquiryApp"));
                startActivity(intent);
                return true;

            case R.id.about_us:
                Intent o=new Intent(MainActivity.this,about_us.class);
                startActivity(o);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
