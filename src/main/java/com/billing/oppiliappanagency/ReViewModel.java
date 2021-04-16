package com.billing.oppiliappanagency;

public class ReViewModel {
    private String Barname;
    private double quantity;
    private double amount;
    private double totalamount;

    public ReViewModel(String barname, double quantity, double amount, double totalamount) {
        Barname = barname;
        this.quantity = quantity;
        this.amount = amount;
        this.totalamount = totalamount;
    }

    public String getBarname() {
        return Barname;
    }

    public void setBarname(String barname) {
        Barname = barname;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(double totalamount) {
        this.totalamount = totalamount;
    }
}
