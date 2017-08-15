package com.example.riteshagrawal.sstc_edrp;

import android.os.Handler;
import android.os.Message;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;


public class attendence_extractor {
String dnld_data;
    String Attancence="";
    Handler handler;
    public attendence_extractor(String dnld_data, Handler handler) {
        this.dnld_data=dnld_data;
        this.handler=handler;
        extractor(dnld_data);
    }

    public void extractor(String dnld_data){
        Document doc = Jsoup.parse(dnld_data,"utf-8");
        //System.out.println(doc);

        Elements tables =doc.getElementsByTag("table");
        System.out.println(tables.size());
        //  System.out.println(tables.get(2));
        Elements tr=tables.get(2).getElementsByTag("tr");
        System.out.println(tr.size());
        Elements td= tr.get(1).getElementsByTag("td");
        System.out.println(td.size());
        System.out.println(td.get(td.size()-1));
        Element AttendenceRow = td.get(td.size()-1);
        System.out.println(AttendenceRow.text());
        Attancence=AttendenceRow.text();

        Message message = Message.obtain();
        message.obj = new customObject("", Attancence);
        handler.sendMessage(message);
    }
}
