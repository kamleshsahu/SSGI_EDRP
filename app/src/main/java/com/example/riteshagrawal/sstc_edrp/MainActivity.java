package com.example.riteshagrawal.sstc_edrp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
Handler handler,handler2;
   static SharedPreferences sd;
    String LoginParams="";
    EditText id;
    EditText pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        id = (EditText) findViewById(R.id.id);
        pass = (EditText) findViewById(R.id.pass);


        sd = this.getSharedPreferences("com.example.riteshagrawal.sstc_edrp", Context.MODE_APPEND);


        //System.out.println("sd lastcall is empty!!!");
        sd.edit().putString("lastcall", "0").apply();




        System.out.println("hii by");


        if(sd.getString("loginParams","").equals("")) {
            sd.edit().putString("loginParams", "").apply();

        }else {
        //  LoginParams = sd.getString("loginParams","");
//            key_pass_generator key_pass_generator= new key_pass_generator(handler,sd);
//            key_pass_generator.start();
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
}
