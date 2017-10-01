package com.example.riteshagrawal.sstc_edrp;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class about_us extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_us);

        //back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



      ImageView  Img = (ImageView) findViewById(R.id.github);
        Img.setOnClickListener(new View.OnClickListener() {
            // Start new list activity
            public void onClick(View v) {
                String url = "https://github.com/kamleshsahu/SSGI_EDRP";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

       ImageView copyText = (ImageView) findViewById(R.id.copy);
        copyText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("","https://github.com/kamleshsahu/SSGI_EDRP");
                clipboard.setPrimaryClip(clip);
                Toast.makeText(about_us.this, "Link Copied", Toast.LENGTH_SHORT).show();
            }
        });
    }
    //back menu
    @Override
    public  boolean onOptionsItemSelected(MenuItem item){
        onBackPressed();
        return true;
    }

    public void textOnClick(View view) {
        view.setSelected(true);
        view.requestFocus();
    }
}
