package com.example.riteshagrawal.sstc_edrp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by Ritesh Agrawal on 16-08-2017.
 */

public class attend_shower extends AppCompatActivity {
    Handler handler,handler2;
    SharedPreferences sd=MainActivity.sd;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attend_shower);


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
                    Toast.makeText(attend_shower.this,"Yehh "+data.getResult()+" % Attendence",Toast.LENGTH_LONG).show();
                }
            }
        };

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
                    new Thread( new Worker(attend_shower.this,"hey baby",sd,handler2)).start();
                }
            }
        };

        key_pass_generator key_pass_generator= new key_pass_generator(handler,sd);
        key_pass_generator.start();
    }
}
