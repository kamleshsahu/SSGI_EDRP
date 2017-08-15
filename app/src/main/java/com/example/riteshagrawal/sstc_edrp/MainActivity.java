package com.example.riteshagrawal.sstc_edrp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
Handler handler,handler2;
    SharedPreferences sd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        sd = this.getSharedPreferences("com.example.riteshagrawal.sstc_edrp", Context.MODE_APPEND);


        //System.out.println("sd lastcall is empty!!!");
        sd.edit().putString("lastcall", "0").apply();

        handler2 = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                //System.out.println("under pre dnld handler");
                customObject data = (customObject) msg.obj;
                System.out.println(data.getResult());
                if(data.getResult().equals("error")){
                    System.out.println("here is error msg :"+data.getErrorMsg());
                }else{
                    Toast.makeText(MainActivity.this,"Yehh 80 % Attendence",Toast.LENGTH_LONG).show();
                }
            }
        };


        System.out.println("hii by");
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                //System.out.println("under pre dnld handler");
                customObject data = (customObject) msg.obj;
                System.out.println(data.getResult());
                if(data.getResult().equals("error")){
                    System.out.println("here is error msg :"+data.getErrorMsg());
                }else{
                    System.out.println("yeh got the cookie :"+sd.getString("cookie",""));

                  new Thread( new Worker(MainActivity.this,"hey baby",sd,handler2)).start();



                }
            }
        };

        key_pass_generator key_pass_generator= new key_pass_generator(handler,sd);
        key_pass_generator.start();

    }
}
