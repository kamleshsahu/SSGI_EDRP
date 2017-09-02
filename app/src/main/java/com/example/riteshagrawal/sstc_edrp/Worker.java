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


import org.jsoup.Jsoup;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import java.net.URL;
import java.nio.charset.StandardCharsets;



public class Worker implements Runnable {
    private Context context;
    private String task_name;
    ConnectivityManager mgr = (ConnectivityManager)MainActivity.appContext.getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo netInfo = mgr.getActiveNetworkInfo();
    private Handler handler;
    Handler dnld_handler,after_UserInfo_dnld, after_attendencedata_dnld;
    private SharedPreferences sd = null;
    String post_loginUrl="http://182.71.130.11/x%40%40%401%40%40%4011/o0xx00x0x0x0x00xx___/default.asp";
    String getDetails_url="http://182.71.130.11/x%40%40%401%40%40%4011/stud@_1276@@@@_@@@@@@/2@@@@@@@@@@att/default.asp";
    String final_call="http://182.71.130.11/x%40%40%401%40%40%4011/stud@_1276@@@@_@@@@@@/2@@@@@@@@@@att/s__att@@@@@@@@@@__s@@@all.asp";
    String logout_url="http://182.71.130.11/x%40%40%401%40%40%4011/logout.asp";

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

        after_attendencedata_dnld = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                System.out.println("worker,after attendence data download");
                customObject data = (customObject) msg.obj;
                System.out.println(data.getResult());
                if(data.getResult().equals("success")){
                    new attendence_extractor(data.getMsg(),handler);
                }else{
                    System.out.println("here is error msg :"+data.getErrorMsg());
                }
            }
        };



        after_UserInfo_dnld= new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                System.out.println("worker,after Userinfo data download");
                customObject data = (customObject) msg.obj;
                System.out.println(data.getResult());
                if(data.getResult().equals("success")){
                    System.out.println("yeh got the data :"+data.getResult());

                    new info_extractor(data.getMsg(),handler);

                }else{
                    System.out.println("here is error msg :"+data.getErrorMsg());
                }
            }
        };

        dnld_handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                System.out.println("worker,under dnld handler");
                customObject data = (customObject) msg.obj;
                System.out.println(data.getResult());
                if(data.getResult().equals("success")){
                    Message message = Message.obtain();
                    message.obj = new customObject("", "success", "");
                    handler.sendMessage(message);
                    System.out.println("yeh got the data :"+data.getResult());
                }else{


                    System.out.println("here is error msg :"+data.getErrorMsg());

                }
            }
        };



        if (netInfo != null && netInfo.isConnected()) {
            task_identifier(task_name);

        }else{
            System.out.println("Before task identifier !!!!.....here is the error :" + "no internet connection....!!!");
            Message message = Message.obtain();
            message.obj = new customObject("", "error", "Please Connect To Internet");
            handler.sendMessage(message);
        }
        Looper.loop();

    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void task_identifier(String task_name){
        switch (task_name){
            case "generate_cookie":
                if(attend_shower.cookie_generated){
                    System.out.println(" task identifier, cookie is already available !!! ");
                    Message message = Message.obtain();
                    message.obj = new customObject("", "success", "");
                    handler.sendMessage(message);
                }else {
                    new cookie_generator(handler, sd).start();
                }
                break;

            case "login":
                Post_Login(handler,"poster",post_loginUrl);
                break;

            case "fetch_attendence":
                finalCall_via_POST(after_attendencedata_dnld,"",final_call,attend_shower.users_info_url);
                break;

            case "fetch_users_info":
                get_Details_via_POST(after_UserInfo_dnld,"poster2",getDetails_url);
                break;

            case "logout":
                Post_Logout(handler,"logout",logout_url );
                break;

            default:
                System.out.println("unknown errror inside worker");


        }
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
            System.out.println("post login ,cookie : "+str2);
                    E.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
                    E.setRequestProperty("Cookie", str2);
                    E.setRequestProperty("Referer", "http://182.71.130.11/x%40%40%401%40%40%4011/default1.asp");
                    E.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.90 Safari/537.36");
                    E.setRequestProperty("Host", "182.71.130.11");
                    E.setRequestProperty("Method", "POST");
         //           E.setRequestProperty("Content-Length", Integer.toString(postDataLength));
                    E.setUseCaches(false);

                    E.setConnectTimeout(240000);
                    E.setReadTimeout(20000);
                    E.setDoInput(true);
                    E.setDoOutput(true);

            DataOutputStream wr = new DataOutputStream(E.getOutputStream());
                wr.write( postData );



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
                         //   System.out.println("hii baby "+inputLine);
                            result += inputLine;
                        }

                     //   System.out.println("hii baby 2 :"+result);
                        org.jsoup.nodes.Document doc = null;
                        try {
                            doc = Jsoup.parse(result, "utf-8");

                            String title_text= doc.getElementsByTag("title").text();
                            System.out.println("here is doc : "+doc);
                            System.out.println("here is href :"+title_text);


                            if(title_text.startsWith("My Home Page")){
                                System.out.println(" downloaded data =" + result);
                                Message message = Message.obtain();
                                message.obj = new customObject(task_name,"success" ,result);
                                dnld_handler.sendMessage(message);
                                attend_shower.logged_in=true;

//                                if(in !=null){
//                                    try {
//                                        in.close();
//                                    } catch (IOException e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//                                if (wr != null) {
//                                    try {
//                                        wr.close();
//                                    } catch (IOException e) {
//                                        e.fillInStackTrace();
//                                    }
//                                }
//                                if (E != null) {
//                                    E.disconnect();
//                                }

                            }else  if(title_text.startsWith("SSCET-eCampus") ){

                                Message message = Message.obtain();
                                message.obj = new customObject("", "error", "Wrong ID or Password");
                                handler.sendMessage(message);
                            }else{
                                Message message = Message.obtain();
                                message.obj = new customObject("", "error", "Wrong ID or Password");
                                handler.sendMessage(message);
                            }
                        }catch (Exception e){
                            e.fillInStackTrace();
                            Message message = Message.obtain();
                            message.obj = new customObject("", "error", e.toString());
                            dnld_handler.sendMessage(message);
                        }
                    }
                } catch (Exception e) {
                    e.fillInStackTrace();
                    System.out.println("here is the error :"+e.toString());
                    Message message = Message.obtain();
                    message.obj = new customObject("", "error", e.toString());
                    dnld_handler.sendMessage(message);
                }


    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void get_Details_via_POST(Handler dnld_handler, String task_name, String urls) {
        String result = "";
        String sendTaskName = task_name;
        URL url;
        String urlParameters  = "info1=21&infox=Attendance&e_cat=&e_id=";
        byte[] postData = urlParameters.getBytes( StandardCharsets.UTF_8 );
        int postDataLength = postData.length;
        ConnectivityManager mgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = mgr.getActiveNetworkInfo();



                try {
                    HttpURLConnection E = null;
                    url = new URL(urls);
                    E = (HttpURLConnection) url.openConnection();
                    System.out.println("calling url :" + urls);
                    String str2 = sd.getString("cookie", "");
                    System.out.println("get details via post ,cookie : " + str2);
                    E.setRequestProperty("Cookie", str2);
                    E.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    E.setRequestProperty("Referer", "http://182.71.130.11/x%40%40%401%40%40%4011/home/options.asp");
                    E.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.90 Safari/537.36");
                    E.setRequestProperty("Host", "182.71.130.11");
                    E.setRequestProperty("Method", "POST");
                    E.setRequestProperty("Content-Length", Integer.toString(postDataLength));
                    E.setUseCaches(false);
                    E.setConnectTimeout(30000);
                    E.setReadTimeout(10000);
                    E.setDoInput(true);
                    E.setDoOutput(true);

                    DataOutputStream wr = new DataOutputStream(E.getOutputStream());
                    wr.write(postData);


                    //     E.connect();
                    System.out.println("response code is :" + E.getResponseCode());
                    if (E.getResponseCode() != 200) {
                        System.out.println("response code is not 200");

                        System.out.println("redirect url is :" + E.getHeaderField("Location"));
                        //                Data_Downloader(dnld_handler, task_name,E.getHeaderField("Location"));

                    } else {
                        System.out.println("Jai hind : " + E.getResponseCode());


                        BufferedReader in = new BufferedReader(
                                new InputStreamReader(E.getInputStream()));


                        String inputLine = null;
                        while ((inputLine = in.readLine()) != null) {
                            System.out.println(inputLine);
                            result += inputLine;
                        }
                        if (in != null) {
                            try {
                                in.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        if (wr != null) {
                            try {
                                wr.close();
                            } catch (IOException e) {
                                e.fillInStackTrace();
                            }
                        }
                        if (E != null) {
                            E.disconnect();
                        }

                        System.out.println(" downloaded data =" + result);
                        Message message = Message.obtain();
                        message.obj = new customObject(task_name, "success", result);
                        dnld_handler.sendMessage(message);
                    }
                } catch (Exception e) {
                    e.fillInStackTrace();
                    System.out.println("here is the error :" + e.toString());
                    Message message = Message.obtain();
                    message.obj = new customObject(task_name, "error", e.toString());
                    handler.sendMessage(message);
                }



    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void finalCall_via_POST(Handler dnld_handler2, String task_name, String urls,String urlParameters) {
        String result = "";
        String sendTaskName = task_name;
        URL url;
        System.out.println("here is urlParameters :"+"\n"+urlParameters);
      //  String urlParameters  = "sub_mit=&holdme=0&txtrollinfo=90&txtbatchinfo=2&cmbcollegename=FET&BranchF=CSE&SemesterF=3&SectionF=B&extsec='B','.'&reporttype=Attendance Report&dc1=17-JUL-2017&dc2=14-AUG-2017&apercent=ALL";
        byte[] postData = urlParameters.getBytes( StandardCharsets.UTF_8 );
        int postDataLength = postData.length;
        DataOutputStream wr=null;
        HttpURLConnection E = null;
        BufferedReader in=null;
        ConnectivityManager mgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = mgr.getActiveNetworkInfo();


            try {

                url = new URL(urls);
                E = (HttpURLConnection) url.openConnection();
                System.out.println("calling url :" + urls);
                String str2 = sd.getString("cookie", "");
                System.out.println("finalcall via post ,cookie : " + str2);
                E.setRequestProperty("Cookie", str2);
                E.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                E.setRequestProperty("Referer", "http://182.71.130.11/x%40%40%401%40%40%4011/stud@_1276@@@@_@@@@@@/2@@@@@@@@@@att/default.asp");
                E.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.90 Safari/537.36");
                E.setRequestProperty("Host", "182.71.130.11");
                E.setRequestProperty("Method", "POST");
                E.setRequestProperty("Content-Length", Integer.toString(postDataLength));
                E.setUseCaches(false);
                E.setConnectTimeout(30000);
                E.setReadTimeout(10000);
                E.setDoInput(true);
                E.setDoOutput(true);

                wr = new DataOutputStream(E.getOutputStream());
                wr.write(postData);


                //     E.connect();
                System.out.println("response code is :" + E.getResponseCode());
                if (E.getResponseCode() != 200) {
                    System.out.println("response code is not 200");

                    System.out.println("redirect url is :" + E.getHeaderField("Location"));
                    //                Data_Downloader(dnld_handler, task_name,E.getHeaderField("Location"));

                } else {
                    System.out.println("Jai hind : " + E.getResponseCode());


                    in = new BufferedReader(
                            new InputStreamReader(E.getInputStream()));


                    String inputLine = null;
                    while ((inputLine = in.readLine()) != null) {
                        System.out.println(inputLine);
                        result += inputLine;
                    }
                    if (in != null) {
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (wr != null) {
                        try {
                            wr.close();
                        } catch (IOException e) {
                            e.fillInStackTrace();
                        }
                    }
                    if (E != null) {
                        E.disconnect();
                    }
                    System.out.println(" downloaded data =" + result);
                    Message message = Message.obtain();
                    message.obj = new customObject(task_name, "success", result);
                    dnld_handler2.sendMessage(message);
                }
            } catch
                    (Exception e) {
                e.fillInStackTrace();
                System.out.println("here is the error :" + e.toString());
                Message message = Message.obtain();
                message.obj = new customObject(task_name, "error", e.toString());
                handler.sendMessage(message);
            }


    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void Post_Logout(Handler dnld_handler, String task_name, String urls) {
        String result = "";
        String sendTaskName = task_name;
        URL url;
           String urlParameters  = "info=myhome";
       // String urlParameters  = sd.getString("loginParams","");
        byte[] postData = urlParameters.getBytes( StandardCharsets.UTF_8 );
        //    int postDataLength = postData.length;
        try {
            HttpURLConnection E = null;
            url = new URL(urls);
            E = (HttpURLConnection) url.openConnection();
            System.out.println("calling url :"+urls);
            String str2 = sd.getString("cookie", "");

            E.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            E.setRequestProperty("Cookie", str2);
            E.setRequestProperty("Referer", "http://182.71.130.11/x%40%40%401%40%40%4011/home/title.asp");
            E.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.90 Safari/537.36");
            E.setRequestProperty("Host", "182.71.130.11");
            E.setRequestProperty("Method", "POST");
            //           E.setRequestProperty("Content-Length", Integer.toString(postDataLength));
            E.setUseCaches(false);

            E.setConnectTimeout(10000);
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
                    //   System.out.println("hii baby "+inputLine);
                    result += inputLine;
                }
                        System.out.println(" downloaded data =" + result);
                        Message message = Message.obtain();
                        message.obj = new customObject(task_name,"success", result);
                        dnld_handler.sendMessage(message);


            }
        } catch (Exception e) {
            e.fillInStackTrace();
            System.out.println("Calling Post_Logout :"+e.toString());
            Message message = Message.obtain();
            message.obj = new customObject(task_name, "error", e.toString());
            dnld_handler.sendMessage(message);
        }


    }



}
