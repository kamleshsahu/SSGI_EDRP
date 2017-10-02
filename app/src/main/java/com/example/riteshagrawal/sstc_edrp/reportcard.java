package com.example.riteshagrawal.sstc_edrp;

public class reportcard {

    private String subject;
    private String percent;
    private String maxmarks;
    private String minmarks;

    private String value;
    private String status;

    public reportcard( String subject, String maxmarks, String minmarks, String value,String percent,String status) {

        this.subject=subject;
        this.maxmarks=maxmarks;
        this.minmarks=minmarks;
        this.value=value;
        this.percent=percent;
        this.status=status;
    }





    public String getSubject() {
        return subject;
    }

    public String getMaxmarks() {
        return maxmarks;
    }

    public String getMinmarks() {
        return minmarks;
    }

    public String getPercent() {
        return (percent+"%");
    }

    public String getStatus() {
        return status;
    }

    public String getValue() {
        return value;
    }
}
