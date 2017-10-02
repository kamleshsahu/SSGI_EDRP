package com.example.riteshagrawal.sstc_edrp;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.riteshagrawal.sstc_edrp.R.id.subject;

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

        TextView sha = (TextView) findViewById(R.id.shankara);
        sha.setOnClickListener(new View.OnClickListener() {
            // Start new list activity
            public void onClick(View v) {
                String url = "http://sstc.ac.in/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        TextView  pit = (TextView) findViewById(R.id.pitech);
        pit.setOnClickListener(new View.OnClickListener() {
            // Start new list activity
            public void onClick(View v) {
                String url = "http://www.pietechraipur.org/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        TextView  ris = (TextView) findViewById(R.id.rishabh);
        ris.setOnClickListener(new View.OnClickListener() {
            // Start new list activity
            public void onClick(View v) {
                String url = "https://www.facebook.com/Rishabh0Nayak";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        TextView  feed = (TextView) findViewById(R.id.feedback);
        feed.setOnClickListener(new View.OnClickListener() {
            // Start new list activity
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:rishabhnayak76@gmail.com"));
                intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

        TextView intro1 = (TextView) findViewById(R.id.intro1);
        intro1.setText(Html.fromHtml("SSTC EDRP Android App is an" +"<b>"+" Open Source Project "+"</b>"+"meant Exclusively and Only for SSTC,Bhilai(S1,S2 and S3 Students).Someone Interested to work on this project,can download the source code,edit,modify and Publish There Modified App.Ideas to improve the App are always Welcome."));

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
