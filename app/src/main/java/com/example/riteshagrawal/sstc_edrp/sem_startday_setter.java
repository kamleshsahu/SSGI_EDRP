package com.example.riteshagrawal.sstc_edrp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class sem_startday_setter extends AppCompatActivity {
DatePicker simpleDatePicker;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sem_startday_setter);
        AttendenceActivity.flag=0;

        //back button
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        submit =(Button)findViewById(R.id.submitButton);
        simpleDatePicker = (DatePicker)findViewById(R.id.simpleDatePicker);
        String myDate = AttendenceActivity.todays_date +" 00:00:00";
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

                AttendenceActivity.fromdate.setText(day+ " "+ AttendenceActivity.months[Integer.parseInt(month)]+","+ year.substring(2));
                System.out.println("to date : "+day + "-" + AttendenceActivity.months[Integer.parseInt(month)] + "-" + year);
                AttendenceActivity.fromDate=day + "-" + AttendenceActivity.months[Integer.parseInt(month)] + "-" + year;

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
                AttendenceActivity.millis = date.getTime();
                System.out.println("here is millis baby : "+ AttendenceActivity.millis);
                System.out.println("here is sem start day before change :"+ AttendenceActivity.sem_start_date);
                AttendenceActivity.sem_start_date=day +"-"+ AttendenceActivity.months[Integer.parseInt(month)]+"-"+year;
                System.out.println("here is sem start day after change :"+ AttendenceActivity.sem_start_date);
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day

                AttendenceActivity.todate.setText(AttendenceActivity.todays_date.split("-")[0]+" "+ AttendenceActivity.todays_date.split("-")[1]+","+ AttendenceActivity.todays_date.split("-")[1].substring(2));
                AttendenceActivity.toDate = AttendenceActivity.todays_date;
                System.out.println("to date : " + AttendenceActivity.todays_date);

                    Users_info_Object obj = new Users_info_Object(
                            AttendenceActivity.sd.getString("c_uname", ""),
                            AttendenceActivity.sd.getString("c_pass", ""),
                            day +"-"+ AttendenceActivity.months[Integer.parseInt(month)]+"-"+year
                    );
                    Thread t = new Thread(new Users_Data_Saver(AttendenceActivity.sd, obj));
                    t.start();


                AttendenceActivity.cookie_generated=false;
                AttendenceActivity.logged_in=false;

                Intent i = new Intent(sem_startday_setter.this ,AttendenceActivity.class);
                i.putExtra("sem_startday_set",true);
                startActivity(i);
                sem_startday_setter.this.finish();

            }
        });
    }
    //back menu
//    @Override
//    public  boolean onOptionsItemSelected(MenuItem item){
//        onBackPressed();
//        return true;
   // }

//    @Override
//    public void onBackPressed() {
//        Intent startMain = new Intent(Intent.ACTION_MAIN);
//        startMain.addCategory(Intent.CATEGORY_HOME);
//        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(startMain);
//    }

}
