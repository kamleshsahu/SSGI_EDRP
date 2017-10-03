package com.example.riteshagrawal.sstc_edrp;


import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;

public class Users_Data_Saver implements Runnable {
    ArrayList<Users_info_Object> list =new ArrayList<Users_info_Object>();
    Users_info_Object item;
    SharedPreferences sd;
    public Users_Data_Saver(SharedPreferences sd,Users_info_Object item) {
        this.item=item;
        this.sd=sd;
    }


    @Override
    public void run() {
        Boolean elementRemoved=false;
        Gson gson = new Gson();
            if(sd.getString("Users_Data_Saver", "").equals("")) {
              //System.out.println("Trains Saver is not there so creating Users_Data_Saver and then adding");
             //   item.setSem_start_date(attend_shower.sem_start_date);
                list.add(item);
           //System.out.println("element added :"+item);
                SharedPreferences.Editor prefsEditor = sd.edit();
                String json = gson.toJson(new UserDataSaverObject(list));
                prefsEditor.putString("Users_Data_Saver", json);
                prefsEditor.commit();
            }else if(!sd.getString("Users_Data_Saver", "").equals("")){
                String json1 = sd.getString("Users_Data_Saver", "");
           //System.out.println("here is json 1" + json1);
                UserDataSaverObject obj = gson.fromJson(json1, UserDataSaverObject.class);
                list=obj.getList();

               int flag =0;
               //System.out.println("list iterator on job...");
                    for(Users_info_Object item0:list){
                        if(item0.getUname().equals(item.getUname()) && item0.getPass().equals(item.getPass())){
                            if(baseactivity.sem_start_date.equals("")) {
                                item.setSem_start_date(item0.getSem_start_date());
                            }else{
                                item.setSem_start_date(baseactivity.sem_start_date);
                            }
                            list.remove(item0);
                            elementRemoved=true;
                       ////System.out.println("element removed :"+item.getTrnNo());
                            list.add(item);
                      //      //System.out.println("element added :"+item);
                            flag=1;
                            break;
                        }
                    }

                    if(flag==0){

                        list.add(item);
                    }


                SharedPreferences.Editor prefsEditor = sd.edit();
                String json = gson.toJson(new UserDataSaverObject(list));
                prefsEditor.putString("Users_Data_Saver", json);
                prefsEditor.commit();
               //System.out.println("here is json 2" + json);
           //System.out.println("creating Users_Data_Saver in sd");
            }else{
           //System.out.println("dont know what to do....");
            }

    }
}
