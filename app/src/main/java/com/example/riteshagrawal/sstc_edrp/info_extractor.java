package com.example.riteshagrawal.sstc_edrp;

import android.os.Handler;
import android.os.Message;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Document;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class info_extractor {
String dnld_data;
    String urlParams="";
    Handler handler;
    public info_extractor(String dnld_data,Handler handler) {
        this.dnld_data=dnld_data;
        this.handler=handler;
        extractor(dnld_data);
    }

    public void extractor(String dnld_data){
        org.jsoup.nodes.Document doc = null;
        ArrayList<key_val> list =new ArrayList<>();
        try {
            doc = Jsoup.parse(dnld_data, "utf-8");

            Elements form = doc.getElementsByTag("form");
            Elements m = form.get(0).getElementsByTag("input");
            int k = 0;
            for (k = 0; k < m.size(); k++) {
                if (m.get(k).attr("type").equals("hidden")) {
                    System.out.println(m.get(k).attr("name") + "=" + m.get(k).attr("value"));
                    list.add(new key_val(m.get(k).attr("name"),m.get(k).attr("value")));
                    urlParams += m.get(k).attr("name") + "=" + m.get(k).attr("value") + "&";
                }
            }
            Elements select = form.get(0).getElementsByTag("select");
            int l = 0;
            for (l = 0; l < select.size() - 1; l++) {
                System.out.print(select.get(l).attr("name"));
                System.out.print("=");
                Element option = select.get(l).getElementsByTag("option").get(0);
                System.out.println(option.attr("value"));
                list.add(new key_val(select.get(l).attr("name"),option.attr("value")));
                urlParams += select.get(l).attr("name") + "=" + option.attr("value") + "&";
            }

            list.add(new key_val("fromDate","01-AUG-2017"));
            list.add(new key_val("toDate","14-AUG-2017"));
            urlParams += "dc1=01-AUG-2017&";
            urlParams += "dc2=14-AUG-2017&";
            urlParams += "apercent=ALL&";
            urlParams += "reporttype=Attendance Report";
            System.out.println("here is url params : " + urlParams);


            attend_shower.list=list;
            Message message = Message.obtain();
            message.obj = new customObject("", urlParams);
            handler.sendMessage(message);
        }catch (Exception e){
            e.fillInStackTrace();
            System.out.println("here is the bug : "+e.toString());
        }


    }
}
