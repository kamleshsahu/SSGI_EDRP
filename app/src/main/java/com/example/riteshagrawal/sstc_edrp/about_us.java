package com.example.riteshagrawal.sstc_edrp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class about_us extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_us);

        //back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



    }
    //back menu
    @Override
    public  boolean onOptionsItemSelected(MenuItem item){
        onBackPressed();
        return true;
    }

}
