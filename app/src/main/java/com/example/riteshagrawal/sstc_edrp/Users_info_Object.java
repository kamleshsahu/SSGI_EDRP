package com.example.riteshagrawal.sstc_edrp;

/**
 * Created by Ritesh Agrawal on 16-08-2017.
 */

public class Users_info_Object {
    private String key;
    private String value;
    private String name;
    private String branch;
    private String sem;
    private String sec;
    private String rollno;
    private String batch;
    private String sem_start_date;
    private String uname;
    private String pass;
    private String clgname;

    public Users_info_Object(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public Users_info_Object(String uname, String pass, String name, String branch, String sem, String sec, String rollno, String batch,String clgname) {
        this.uname = uname;
        this.batch=batch;
        this.branch=branch;
        this.sem=sem;
        this.rollno=rollno;
        this.sec=sec;
        this.name=name;
        this.pass=pass;
        this.clgname=clgname;
    }

    public Users_info_Object(String uname, String pass, String sem_start_date) {
        this.uname = uname;
        this.pass=pass;
        this.sem_start_date = sem_start_date;
    }

    public void setSem_start_date(String sem_start_date) {
        this.sem_start_date = sem_start_date;
    }

    public String getUname() {
        return uname;
    }

    public String getPass() {
        return pass;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public String getBatch() {
        return batch;
    }

    public String getBranch() {
        return branch;
    }

    public String getRollno() {
        return rollno;
    }

    public String getSec() {
        return sec;
    }

    public String getSem() {
        return sem;
    }

    public String getSem_start_date() {
        return sem_start_date;
    }

    public String getClgname() {
        return clgname;
    }


}
