package com.billing.oppiliappanagency;

public class BillModel {
    String Bname;
    String Q_bill;
    String A_bill;
    String Ta_bill;

    public BillModel(String bname, String q_bill, String a_bill, String ta_bill) {
        Bname = bname;
        Q_bill = q_bill;
        A_bill = a_bill;
        Ta_bill = ta_bill;
    }

    public String getBname() {
        return Bname;
    }

    public void setBname(String bname) {
        Bname = bname;
    }

    public String getQ_bill() {
        return Q_bill;
    }

    public void setQ_bill(String q_bill) {
        Q_bill = q_bill;
    }

    public String getA_bill() {
        return A_bill;
    }

    public void setA_bill(String a_bill) {
        A_bill = a_bill;
    }

    public String getTa_bill() {
        return Ta_bill;
    }

    public void setTa_bill(String ta_bill) {
        Ta_bill = ta_bill;
    }
}
