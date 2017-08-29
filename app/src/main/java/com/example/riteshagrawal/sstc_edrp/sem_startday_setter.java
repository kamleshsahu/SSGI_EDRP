package com.example.riteshagrawal.sstc_edrp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class sem_startday_setter extends AppCompatActivity {
DatePicker simpleDatePicker;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sem_startday_setter);


        submit =(Button)findViewById(R.id.submitButton);
        simpleDatePicker = (DatePicker)findViewById(R.id.simpleDatePicker);
        String myDate =attend_shower.todays_date +" 00:00:00";
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(myDate);
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("bug in the  simple date format >>"+e.toString());
        }



        simpleDatePicker.setMaxDate(date.getTime());
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String day = "" + simpleDatePicker.getDayOfMonth();
                String month = "" + (simpleDatePicker.getMonth() );
                String year = "" + simpleDatePicker.getYear();

                attend_shower.fromdate.setText(day+ " "+attend_shower.monthsD[Integer.parseInt(month)]);
                System.out.println("to date : "+day + "-" + attend_shower.months[Integer.parseInt(month)] + "-" + year);
                attend_shower.fromDate=day + "-" + attend_shower.months[Integer.parseInt(month)] + "-" + year;

                Toast.makeText(getApplicationContext(), day + "-" + month + "-" + year, Toast.LENGTH_LONG).show();

                System.out.println("date picked : "+day + "-" + month + "-" + year);

                String myDate =day+"-"+month+"-"+year +" 00:00:00";
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                Date date = null;
                try {
                    date = sdf.parse(myDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                    System.out.println("bug in the  simple date format >>"+e.toString());
                }
                attend_shower.millis = date.getTime();
                System.out.println("here is millis baby : "+attend_shower.millis);
                attend_shower.sem_start_date=day +"-"+attend_shower.months[Integer.parseInt(month)]+"-"+year;
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day

                attend_shower.todate.setText(attend_shower.todays_date);
                attend_shower.toDate = attend_shower.todays_date;
                System.out.println("to date : " +attend_shower.todays_date);
                key_val obj = new key_val(
                        attend_shower.sd.getString("c_uname", ""),
                        attend_shower.sd.getString("c_pass", ""),
                        attend_shower.fromDate
                );

                Thread t = new Thread(new Users_Data_Saver(attend_shower.sd, obj));
                t.start();
                Intent i = new Intent(sem_startday_setter.this ,attend_shower.class);
                i.putExtra("sem_startday_set",true);
                startActivity(i);
                sem_startday_setter.this.finish();

            }
        });
    }

}
