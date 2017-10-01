package com.example.riteshagrawal.sstc_edrp;

import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.net.CookieManager;

import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

import java.util.Date;

import java.util.List;


public class cookie_generator extends Thread {

static SharedPreferences sd;

    static boolean gotthekey=false;
    private static Handler handler;



    public cookie_generator() {

    }

    public cookie_generator(Handler handler, SharedPreferences sd){
        this.handler=handler;
        this.sd=sd;
    }
static void getkeyval()

    {
        try {
            if((new Date()).getTime() - Long.parseLong(sd.getString("lastcall","")) >= 240000 ) {
           //System.out.println("calling keypass url.........");
                DownloadTask task = new DownloadTask();
                task.seturl("http://182.71.130.11/x%40%40%401%40%40%4011/default1.asp");
                task.doInBackground();
            }else{
           //System.out.println("no need to call keypass");

                Message message = Message.obtain();
                message.obj =new customObject("cookie_generator","success","already having..no need to call keypass");
                handler.sendMessage(message);
            }
        } catch (Exception e) {
       //System.out.println("error inside cookie_generator :"+e.fillInStackTrace());
            String msgSend ="error inside cookie_generator :"+e.fillInStackTrace();
            Message message = Message.obtain();
            message.obj =new customObject("cookie_generator","error","Pls Check Your Internet Connection");
            handler.sendMessage(message);
        }
    }

    @Override
    public void run() {
        Thread.currentThread().setName("key_pass_thread");
       // Log.i("current Thread name :",Thread.currentThread().getName());
        getkeyval();
    }

    public static class DownloadTask  {
         private String uRl;

        public void seturl(String uRl) {
            this.uRl = uRl;
        }
        protected void doInBackground() {
            String result = "";
            URL url;

           // Log.i("Thread name :",Thread.currentThread().getName());
            HttpURLConnection urlConnection = null;

            try {
            //System.out.println("under downloading function \ncalling url "+uRl);
                url = new URL(uRl);

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setConnectTimeout(240000);
                urlConnection.setReadTimeout(8000);
                urlConnection.connect();
                Object localObject2;
                if (urlConnection.getResponseCode() == 200) {
                 //System.out.println("connnection url is 200......");
                    Object localObject3 = new CookieManager();
                    Object localObject1;
                    localObject1 = (List) urlConnection.getHeaderFields().get("Set-Cookie");
                    Object localObject4;
                    localObject4 = null;
                     Log.i("cookie found :", String.valueOf(urlConnection.getHeaderFields().get("Set-Cookie").get(0)));


                    List<String> k=urlConnection.getHeaderFields().get("Set-Cookie");
                    System.out.println("array to string :"+k.get(0).split(";")[0]);

                    localObject3 = urlConnection.getHeaderFields().get("Set-Cookie");
                                    localObject3 = urlConnection.getHeaderFields().get("Set-Cookie").get(0);
                                    Log.i("cookie ", (localObject3.toString()));
                                    sd.edit().putString("cookie", localObject3.toString()).apply();

                    result= localObject1.toString();

                                    gotthekey=true;
                                    baseactivity.cookie_generated=true;






                    }

                  if(gotthekey){
                      Message message = Message.obtain();
                      message.obj =new customObject("","success","");
                      handler.sendMessage(message);
                  }else{
                      Message message = Message.obtain();
                      message.obj =new customObject("cookie_generator","error","unknown error.....");
                      handler.sendMessage(message);
                  }



            }catch (SocketTimeoutException e){
           //System.out.println("Socket Timeout Exception:"+e.fillInStackTrace());

                Message message = Message.obtain();
                message.obj =new customObject("cookie_generator","error","Server Timeout .Pls Retry");
                handler.sendMessage(message);
            }
            catch (Exception e) {

          //System.out.println("Error inside key pass generator 2:"+e.fillInStackTrace());
                String msgSend="Error inside key pass generator 2:"+e.fillInStackTrace();
                Message message = Message.obtain();
                message.obj =new customObject("cookie_generator","error","Pls Check Your Internet Connection");
                handler.sendMessage(message);

            }
        }
    }
}
