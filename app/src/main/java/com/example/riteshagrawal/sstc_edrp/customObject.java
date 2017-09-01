package com.example.riteshagrawal.sstc_edrp;

import java.util.ArrayList;

/**
 * Created by sahu on 6/3/2017.
 */

public class customObject {
    private String task_name;
    private String result;
    private String senderId;
    private String msg;
    private String dnlddata;
    private String[]RunDaysInt;



    private ArrayList<Users_info_Object> infoList;



    public void setInfoList(ArrayList<Users_info_Object> infoList) {
        this.infoList = infoList;
    }

    public ArrayList<Users_info_Object> getInfoList() {
        return infoList;
    }




    public customObject(String senderId, String result, String msg) {
        this.senderId=senderId;
        this.result=result;
        this.msg=msg;
    }



    public String getResult() {
        return result;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getErrorMsg() {
        return msg;
    }
    public String getMsg() {
        return msg;
    }
    public String[] getRunDaysInt() {
        return RunDaysInt;
    }

    public void setRunDaysInt(String[] runDaysInt) {
        RunDaysInt = runDaysInt;
    }
}
