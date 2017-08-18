package com.example.riteshagrawal.sstc_edrp;

public class attend_info_class {
    private String teacher;
    private String subject;
    private String sub_desc;
    private String outOf;
    private String value;


    public attend_info_class(String teacher, String subject, String desc, String outOf, String value) {
        this.teacher=teacher;
        this.subject=subject;
        this.sub_desc=desc;
        this.outOf=outOf;
        this.value=value;
    }

    public String getSub_Desc() {
        return sub_desc;
    }

    public String getOutOf() {
        return outOf;
    }

    public String getSubject() {
        return subject;
    }

    public String getTeacher() {
        return teacher;
    }

    public String getValue() {
        return value;
    }
}
