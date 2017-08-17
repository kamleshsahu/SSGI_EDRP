package com.example.riteshagrawal.sstc_edrp;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.annotation.UiThread;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;


public class Worker implements Runnable {
    private Context context;
    private String task_name;
    private String from_stn, to_stn;
    private String stn_code;
    private String train_no;
    private Handler handler, key_handler;
    Handler dnld_handler,dnld_handler_2, dnld_handler_3, info_ext_handler;
    private SharedPreferences sd = null;

    private String filter;
    private String[] dateobj;
    private String TrnStartDate;
    String postUrl="http://182.71.130.11/x%40%40%401%40%40%4011/o0xx00x0x0x0x00xx___/default.asp";
    String getDetails_url="http://182.71.130.11/x%40%40%401%40%40%4011/stud@_1276@@@@_@@@@@@/2@@@@@@@@@@att/default.asp";
    String final_call="http://182.71.130.11/x%40%40%401%40%40%4011/stud@_1276@@@@_@@@@@@/2@@@@@@@@@@att/s__att@@@@@@@@@@__s@@@all.asp";

    public Worker(Context context, String task_name,SharedPreferences sd,Handler handlermain) {
        this.context = context;
        this.task_name = task_name;
        this.sd=sd;
        this.handler=handlermain;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void run() {
        Looper.prepare();

        dnld_handler_3 = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                //System.out.println("under pre dnld handler");
                customObject data = (customObject) msg.obj;
                System.out.println(data.getResult());
                if(data.getResult().equals("error")){
                    System.out.println("here is error msg :"+data.getErrorMsg());
                }else{
//                    System.out.println("yeh got the data :"+data.getResult());
//                    Message message = Message.obtain();
//                    message.obj = new customObject(task_name, data.getResult());
//                    handler.sendMessage(message);

                   new attendence_extractor(data.getResult(),handler);

                }
            }
        };


        info_ext_handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                //System.out.println("under pre dnld handler");
                customObject data = (customObject) msg.obj;
                System.out.println(data.getResult());
                if(data.getResult().equals("error")){
                    System.out.println("here is error msg :"+data.getErrorMsg());
                }else{
                    System.out.println("yeh got the data :"+data.getResult());

                     finalCall_via_POST(dnld_handler_3,"",final_call,data.getResult());




                }
            }
        };

        dnld_handler_2 = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                //System.out.println("under pre dnld handler");
                customObject data = (customObject) msg.obj;
                System.out.println(data.getResult());
                if(data.getResult().equals("error")){
                    System.out.println("here is error msg :"+data.getErrorMsg());
                }else{
                    System.out.println("yeh got the data :"+data.getResult());

                    // finalCall_via_POST(dnld_handler_3,"",final_call);
                       new info_extractor(data.getResult(),info_ext_handler);
                }
            }
        };

        dnld_handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                //System.out.println("under pre dnld handler");
                customObject data = (customObject) msg.obj;
                System.out.println(data.getResult());
                if(data.getResult().equals("error")){
                    System.out.println("here is error msg :"+data.getErrorMsg());
                }else{
                    System.out.println("yeh got the data :"+data.getResult());

                     get_Details_via_POST(dnld_handler_2,"poster2",getDetails_url);

                }
            }
        };

        Post_Login(dnld_handler,"poster",postUrl);
        Looper.loop();

    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void Post_Login(Handler dnld_handler, String task_name, String urls) {
        String result = "";
        String sendTaskName = task_name;
        URL url;
     //   String urlParameters  = "uname=0201160139&password=9981140217&cmbsession=JUL-17";
        String urlParameters  = sd.getString("loginParams","");
        byte[] postData = urlParameters.getBytes( StandardCharsets.UTF_8 );
    //    int postDataLength = postData.length;
        try {
                    HttpURLConnection E = null;
                    url = new URL(urls);
                    E = (HttpURLConnection) url.openConnection();
                    System.out.println("calling url :"+urls);
                    String str2 = sd.getString("cookie", "");
                   // str2 = str2.replaceAll("\\s", "").split("\\[", 2)[1].split("\\]", 2)[0];\
                    E.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
                    E.setRequestProperty("Cookie", str2);
                    E.setRequestProperty("Referer", "http://182.71.130.11/x%40%40%401%40%40%4011/default1.asp");
                    E.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.90 Safari/537.36");
                    E.setRequestProperty("Host", "182.71.130.11");
                    E.setRequestProperty("Method", "POST");
         //           E.setRequestProperty("Content-Length", Integer.toString(postDataLength));
                    E.setUseCaches(false);

                    E.setConnectTimeout(5000);
                    E.setReadTimeout(5000);
                    E.setDoInput(true);
                    E.setDoOutput(true);

            try(DataOutputStream wr = new DataOutputStream(E.getOutputStream())) {
                wr.write( postData );
            }


                  //  E.connect();
                    System.out.println("response code is :"+E.getResponseCode());
                    if (E.getResponseCode() != 200) {
                     System.out.println("response code is not 200");

                     System.out.println("redirect url is :"+E.getHeaderField("Location"));
        //                Data_Downloader(dnld_handler, task_name,E.getHeaderField("Location"));

                    }
                        else {
                     System.out.println("Jai hind : " + E.getResponseCode());


                        BufferedReader in = new BufferedReader(
                                new InputStreamReader(E.getInputStream()));


                        String inputLine = null;
                        while ((inputLine = in.readLine()) != null) {
                            result += inputLine;
                        }

                     System.out.println(" downloaded data =" + result);
                        Message message = Message.obtain();
                        message.obj = new customObject(task_name, result);
                        dnld_handler.sendMessage(message);
                    }
                } catch (Exception e) {
            e.fillInStackTrace();
            System.out.println("here is the error :"+e.toString());
                    Message message = Message.obtain();
                    message.obj = new customObject("", "error", "Error Connecting to Server.Pls Retry");
                    dnld_handler.sendMessage(message);
                }


    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void get_Details_via_POST(Handler dnld_handler2, String task_name, String urls) {
        String result = "";
        String sendTaskName = task_name;
        URL url;
        String urlParameters  = "info1=21&infox=Attendance&e_cat=&e_id=";
        byte[] postData = urlParameters.getBytes( StandardCharsets.UTF_8 );
        int postDataLength = postData.length;
        try {
            HttpURLConnection E = null;
            url = new URL(urls);
            E = (HttpURLConnection) url.openConnection();
            System.out.println("calling url :"+urls);
            String str2 = sd.getString("cookie", "");
            // str2 = str2.replaceAll("\\s", "").split("\\[", 2)[1].split("\\]", 2)[0];
            E.setRequestProperty("Cookie", str2);
            E.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            E.setRequestProperty("Referer", "http://182.71.130.11/x%40%40%401%40%40%4011/home/options.asp");
            E.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.90 Safari/537.36");
            E.setRequestProperty("Host", "182.71.130.11");
            E.setRequestProperty("Method", "POST");
            E.setRequestProperty("Content-Length", Integer.toString(postDataLength ));
            E.setUseCaches(false);
//            E.setRequestProperty("info1","21");
//            E.setRequestProperty("infox","Attendance");
//            E.setRequestProperty("e_id","");
//            E.setRequestProperty("e_cat","");
            E.setConnectTimeout(5000);
            E.setReadTimeout(5000);
            E.setDoInput(true);
            E.setDoOutput(true);

            try(DataOutputStream wr = new DataOutputStream(E.getOutputStream())) {
                wr.write( postData );
            }

       //     E.connect();
            System.out.println("response code is :"+E.getResponseCode());
            if (E.getResponseCode() != 200) {
                System.out.println("response code is not 200");

                System.out.println("redirect url is :"+E.getHeaderField("Location"));
                //                Data_Downloader(dnld_handler, task_name,E.getHeaderField("Location"));

            }
            else {
                System.out.println("Jai hind : " + E.getResponseCode());


                BufferedReader in = new BufferedReader(
                        new InputStreamReader(E.getInputStream()));


                String inputLine = null;
                while ((inputLine = in.readLine()) != null) {
                    System.out.println(inputLine);
                    result += inputLine;
                }

                System.out.println(" downloaded data =" + result);
                Message message = Message.obtain();
                message.obj = new customObject(task_name, result);
                dnld_handler2.sendMessage(message);
            }
        } catch (Exception e) {
            e.fillInStackTrace();
            System.out.println("here is the error :"+e.toString());
            Message message = Message.obtain();
            message.obj = new customObject("", "error", "Error Connecting to Server.Pls Retry");
            dnld_handler2.sendMessage(message);
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void finalCall_via_POST(Handler dnld_handler2, String task_name, String urls,String urlParameters) {
        String result = "";
        String sendTaskName = task_name;
        URL url;
      //  String urlParameters  = "sub_mit=&holdme=0&txtrollinfo=90&txtbatchinfo=2&cmbcollegename=FET&BranchF=CSE&SemesterF=3&SectionF=B&extsec='B','.'&reporttype=Attendance Report&dc1=17-JUL-2017&dc2=14-AUG-2017&apercent=ALL";
        byte[] postData = urlParameters.getBytes( StandardCharsets.UTF_8 );
        int postDataLength = postData.length;
        try {
            HttpURLConnection E = null;
            url = new URL(urls);
            E = (HttpURLConnection) url.openConnection();
            System.out.println("calling url :"+urls);
            String str2 = sd.getString("cookie", "");
            // str2 = str2.replaceAll("\\s", "").split("\\[", 2)[1].split("\\]", 2)[0];
            E.setRequestProperty("Cookie", str2);
            E.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            E.setRequestProperty("Referer", "http://182.71.130.11/x%40%40%401%40%40%4011/stud@_1276@@@@_@@@@@@/2@@@@@@@@@@att/default.asp");
            E.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.90 Safari/537.36");
            E.setRequestProperty("Host", "182.71.130.11");
            E.setRequestProperty("Method", "POST");
            E.setRequestProperty("Content-Length", Integer.toString(postDataLength ));
            E.setUseCaches(false);
//            E.setRequestProperty("info1","21");
//            E.setRequestProperty("infox","Attendance");
//            E.setRequestProperty("e_id","");
//            E.setRequestProperty("e_cat","");
            E.setConnectTimeout(5000);
            E.setReadTimeout(5000);
            E.setDoInput(true);
            E.setDoOutput(true);

            try(DataOutputStream wr = new DataOutputStream(E.getOutputStream())) {
                wr.write( postData );
            }

            //     E.connect();
            System.out.println("response code is :"+E.getResponseCode());
            if (E.getResponseCode() != 200) {
                System.out.println("response code is not 200");

                System.out.println("redirect url is :"+E.getHeaderField("Location"));
                //                Data_Downloader(dnld_handler, task_name,E.getHeaderField("Location"));

            }
            else {
                System.out.println("Jai hind : " + E.getResponseCode());


                BufferedReader in = new BufferedReader(
                        new InputStreamReader(E.getInputStream()));


                String inputLine = null;
                while ((inputLine = in.readLine()) != null) {
                    System.out.println(inputLine);
                    result += inputLine;
                }

                System.out.println(" downloaded data =" + result);
                Message message = Message.obtain();
                message.obj = new customObject(task_name, result);
                dnld_handler2.sendMessage(message);
            }
        } catch (Exception e) {
            e.fillInStackTrace();
            System.out.println("here is the error :"+e.toString());
            Message message = Message.obtain();
            message.obj = new customObject("", "error", "Error Connecting to Server.Pls Retry");
            dnld_handler2.sendMessage(message);
        }


    }

}
