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
    private String SrcStn;
    private String DstnStn;
    private String TrainName;
    private String TrainNo;
    private String TrnStartDate;
    private int TrainCurrPos;

    private ArrayList<key_val> infoList;

    public int getTrainCurrPos() {
        return TrainCurrPos;
    }

    public void setTrnStartDate(String trnStartDate) {
        TrnStartDate = trnStartDate;
    }

    public String getTrnStartDate() {
        return TrnStartDate;
    }

    public void setInfoList(ArrayList<key_val> infoList) {
        this.infoList = infoList;
    }

    public ArrayList<key_val> getInfoList() {
        return infoList;
    }

    public String getTrainName() {
        return TrainName;
    }

    public String getTrainNo() {
        return TrainNo;
    }

    public void setTrainName(String trainName) {
        TrainName = trainName;
    }

    public void setTrainNo(String trainNo) {
        TrainNo = trainNo;
    }

    public void setSrcStn(String srcStn) {
        SrcStn = srcStn;
    }

    public void setDstnStn(String dstnStn) {
        DstnStn = dstnStn;
    }

    public String getDstnStn() {
        return DstnStn;
    }

    public String getSrcStn() {
        return SrcStn;
    }

    public customObject(String task_name, String result) {
       this.task_name=task_name;
        this.result=result;
    }
    public customObject(String senderId, String result, String msg) {
        this.senderId=senderId;
        this.result=result;
        this.msg=msg;
    }


    public void setTrainCurrPos(int trainCurrPos) {
        TrainCurrPos = trainCurrPos;
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

    public String[] getRunDaysInt() {
        return RunDaysInt;
    }

    public void setRunDaysInt(String[] runDaysInt) {
        RunDaysInt = runDaysInt;
    }
}
