package com.example.riteshagrawal.sstc_edrp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
Handler handler,handler2;
   static SharedPreferences sd;
    String LoginParams="";
    AutoCompleteTextView id;
    EditText pass;

    private saver_class countryAdapter;
    private ArrayList<saver_class> countryList= new ArrayList<>();
    private ObjectAdapter obj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        id = (AutoCompleteTextView) findViewById(R.id.id);


   //     id = (EditText) findViewById(R.id.id);
        id.requestFocus();
        pass = (EditText) findViewById(R.id.pass);
       countryList.add(new saver_class("BE20160467","9644790733","Kamlesh Sahu"));
        countryList.add(new saver_class("BE20160826","9617340924","Somnath Trivedi"));
        countryList.add(new saver_class("BE20170287","9982313173","Mohhamaad"));






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
                LoginParams="uname="+countryList.get(i).getId()+"&"+"password="+countryList.get(0).getPass()+"&cmbsession=JUL-17";
                sd.edit().putString("loginParams", LoginParams).apply();
                Intent i1 = new Intent(MainActivity.this, attend_shower.class);
                startActivity(i1);
            }
        });


        sd = this.getSharedPreferences("com.example.riteshagrawal.sstc_edrp", Context.MODE_APPEND);


        //System.out.println("sd lastcall is empty!!!");
        sd.edit().putString("lastcall", "0").apply();




        System.out.println("hii by");


        if(sd.getString("loginParams","").equals("")) {
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
            LoginParams="uname="+id.getText()+"&"+"password="+pass.getText()+"&cmbsession=JUL-17";
            sd.edit().putString("loginParams", LoginParams).apply();
            Intent i = new Intent(MainActivity.this, attend_shower.class);
            startActivity(i);
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
}
