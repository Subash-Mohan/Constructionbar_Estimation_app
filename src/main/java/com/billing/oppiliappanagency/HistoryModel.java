package com.billing.oppiliappanagency;

public class HistoryModel {
    private String hisname;
    private String hisquantity;
    private String hisamount;

    public HistoryModel(String hisname, String hisquantity, String hisamount) {
        this.hisname = hisname;
        this.hisquantity = hisquantity;
        this.hisamount = hisamount;
    }

    public String getHisname() {
        return hisname;
    }

    public void setHisname(String hisname) {
        this.hisname = hisname;
    }

    public String getHisquantity() {
        return hisquantity;
    }

    public void setHisquantity(String hisquantity) {
        this.hisquantity = hisquantity;
    }

    public String getHisamount() {
        return hisamount;
    }

    public void setHisamount(String hisamount) {
        this.hisamount = hisamount;
    }
}
