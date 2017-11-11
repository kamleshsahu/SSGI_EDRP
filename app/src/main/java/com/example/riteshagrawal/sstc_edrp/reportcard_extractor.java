package com.example.riteshagrawal.sstc_edrp;

import android.os.Handler;
import android.os.Message;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;


public class reportcard_extractor {
String dnld_data;

    Handler handler;
    public reportcard_extractor(String dnld_data, Handler handler) {
        this.dnld_data=dnld_data;
        this.handler=handler;
        extractor(dnld_data);
    }

    public void extractor(String dnld_data) {

        try {

            ArrayList<reportcard> datalist=new ArrayList<>();
            Document doc =Jsoup.parse(dnld_data, "utf-8");


            Elements tables =doc.getElementsByTag("table");


               Elements tr = tables.get(1).getElementsByTag("tr");
            if(tr.size()>1) {
                for (int m = 1; m < tr.size(); m++) {
                    Elements td1 = tr.get(m).getElementsByTag("td");
                    datalist.add(new reportcard(td1.get(0).text(), td1.get(1).text(), td1.get(2).text(), td1.get(3).text(), td1.get(4).text(), td1.get(5).text()));
                }


                for (int k = 0; k < datalist.size(); k++) {
                    reportcard r = (reportcard) datalist.get(k);
                    ////System.out.println(r.getSubject() + "\n" + r.getMaxmarks() + "\n" + r.getMaxmarks() + "\n" + r.getMinmarks() + "\n" + r.getValue() + "\n" + r.getPercent() + "\n" + r.getStatus());
                    ////System.out.println("\n");
                }

                // attend_shower.datalist = datalist;
                Message message = Message.obtain();
                customObject obj = new customObject("", "success", "");
                obj.setRclist(datalist);
                message.obj = obj;
                handler.sendMessage(message);
            }else {
                Message message = Message.obtain();
                customObject obj = new customObject("", "error", "Result not yet Published");
            //    obj.setRclist(datalist);
                message.obj = obj;
                handler.sendMessage(message);
            }
        } catch (Exception e) {
               e.fillInStackTrace();
            ////System.out.println("attendence_extractor , attendence_extractor");
            Message message = Message.obtain();
            message.obj = new customObject("", "error", "reportcard_extractor error:"+e.toString());
            handler.sendMessage(message);
        }
    }
}
