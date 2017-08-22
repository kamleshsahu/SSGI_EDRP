package com.example.riteshagrawal.sstc_edrp;

import java.util.ArrayList;

/**
 * Created by sahu on 6/8/2017.
 */

public class UserDataSaverObject {
    ArrayList<key_val> list;
    public UserDataSaverObject(ArrayList<key_val> list) {
       this.list=list;
    }

    public ArrayList<key_val> getList() {
        return list;
    }
}
