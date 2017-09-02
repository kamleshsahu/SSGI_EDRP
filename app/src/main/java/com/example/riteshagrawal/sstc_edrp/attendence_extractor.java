package com.example.riteshagrawal.sstc_edrp;

import android.os.Handler;
import android.os.Message;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.util.ArrayList;


public class attendence_extractor {
String dnld_data;
    String Attancence="";
    Handler handler;
    public attendence_extractor(String dnld_data, Handler handler) {
        this.dnld_data=dnld_data;
        this.handler=handler;
        extractor(dnld_data);
    }

    public void extractor(String dnld_data) {

        try {

            ArrayList<attend_info_class> datalist = new ArrayList<>();
            Document doc = Jsoup.parse(dnld_data, "utf-8");
            ////system.out.println(doc);

            Elements tables = doc.getElementsByTag("table");
            Elements tr = tables.get(1).getElementsByTag("tr");

            Elements td1 = tr.get(1).getElementsByTag("td");
            Elements td2 = tr.get(2).getElementsByTag("td");


            for (int k = 0; k < td2.size(); k++) {
                Element key = td1.get(k);
                Element value = td2.get(k);

                String[] kk = key.text().split(" ");
                //  //system.out.println(kk.length);
                if (kk.length == 4) {
                    datalist.add(new attend_info_class(kk[0], kk[1], kk[2], kk[3], value.text()));
                } else if (kk.length == 2) {
                    // datalist.add(new attend_info_class("","",kk[0],kk[1],value.text()));
                    if (kk[0].startsWith("Tot")) {
                        attend_shower.Total_lectures = kk[1];
                        attend_shower.Attended_lectures = value.text();
                    }

                } else {
                    // datalist.add(new attend_info_class("","",kk[0],"",value.text()));
                    if (kk[0].startsWith("studentname")) {
                        attend_shower.StudentName = value.text();
                    } else if (kk[0].startsWith("%")) {
                        Attancence = value.text();
                        //system.out.println("yipeee got the attendence % :" + Attancence);
                    }
                }
            }


            //   //system.out.println(datalist);
            for (int k = 0; k < datalist.size(); k++) {
                //system.out.println(datalist.get(k).getSubject() + " " + datalist.get(k).getOutOf() + ":" + datalist.get(k).getValue());
            }

            attend_shower.datalist = datalist;
            Message message = Message.obtain();
            message.obj = new customObject("", "success", Attancence);
            handler.sendMessage(message);
        } catch (Exception e) {
               e.fillInStackTrace();
            //system.out.println("attendence_extractor , attendence_extractor");
            Message message = Message.obtain();
            message.obj = new customObject("", "error", "attendence_extractor error:"+e.toString());
            handler.sendMessage(message);
        }
    }
}
