package com.example.riteshagrawal.sstc_edrp;

/**
 * Created by Ritesh Agrawal on 19-08-2017.
 */

public class saver_class {

    private String id;
    private String pass;
    private String name;

    public saver_class(String id,String pass,String name) {
        this.id=id;
        this.pass=pass;
        this.name=name;
    }

    public String getId() {
        return id;
    }

    public String getPass() {
        return pass;
    }

    public String getName() {
        return name;
    }
}
