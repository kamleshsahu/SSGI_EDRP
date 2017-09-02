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
        attend_shower.flag=0;

        //back button
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        submit =(Button)findViewById(R.id.submitButton);
        simpleDatePicker = (DatePicker)findViewById(R.id.simpleDatePicker);
        String myDate =attend_shower.todays_date +" 00:00:00";
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
        Date date = null;

        try {
            date = sdf.parse(myDate);
        } catch (ParseException e) {
            e.printStackTrace();
            //system.out.println("bug in the  simple date format >>"+e.toString());
        }

        simpleDatePicker.setMaxDate(date.getTime());
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String day = "" + simpleDatePicker.getDayOfMonth();
                String month = "" + (simpleDatePicker.getMonth() );
                String year = "" + simpleDatePicker.getYear();

                attend_shower.fromdate.setText(day+ " "+attend_shower.months[Integer.parseInt(month)]+","+ year.substring(2));
                //system.out.println("to date : "+day + "-" + attend_shower.months[Integer.parseInt(month)] + "-" + year);
                attend_shower.fromDate=day + "-" + attend_shower.months[Integer.parseInt(month)] + "-" + year;

                Toast.makeText(getApplicationContext(), day + "-" + month + "-" + year, Toast.LENGTH_LONG).show();

                //system.out.println("date picked : "+day + "-" + month + "-" + year);

                String myDate =day+"-"+month+"-"+year +" 00:00:00";
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                Date date = null;
                try {
                    date = sdf.parse(myDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                    //system.out.println("bug in the  simple date format >>"+e.toString());
                }
                attend_shower.millis = date.getTime();
                //system.out.println("here is millis baby : "+attend_shower.millis);
                //system.out.println("here is sem start day before change :"+attend_shower.sem_start_date);
                attend_shower.sem_start_date=day +"-"+attend_shower.months[Integer.parseInt(month)]+"-"+year;
                //system.out.println("here is sem start day after change :"+attend_shower.sem_start_date);
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day

                attend_shower.todate.setText(attend_shower.todays_date.split("-")[0]+" "+attend_shower.todays_date.split("-")[1]+","+attend_shower.todays_date.split("-")[1].substring(2));
                attend_shower.toDate = attend_shower.todays_date;
                //system.out.println("to date : " +attend_shower.todays_date);

                    Users_info_Object obj = new Users_info_Object(
                            attend_shower.sd.getString("c_uname", ""),
                            attend_shower.sd.getString("c_pass", ""),
                            day +"-"+attend_shower.months[Integer.parseInt(month)]+"-"+year
                    );
                    Thread t = new Thread(new Users_Data_Saver(attend_shower.sd, obj));
                    t.start();


                attend_shower.cookie_generated=false;
                attend_shower.logged_in=false;

                Intent i = new Intent(sem_startday_setter.this ,attend_shower.class);
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

    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }

}
