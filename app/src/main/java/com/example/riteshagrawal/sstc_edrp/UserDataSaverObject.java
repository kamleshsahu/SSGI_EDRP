package com.example.riteshagrawal.sstc_edrp;

import java.util.ArrayList;

/**
 * Created by sahu on 6/8/2017.
 */

public class UserDataSaverObject {
    ArrayList<Users_info_Object> list;
    public UserDataSaverObject(ArrayList<Users_info_Object> list) {
       this.list=list;
    }

    public ArrayList<Users_info_Object> getList() {
        return list;
    }
}
