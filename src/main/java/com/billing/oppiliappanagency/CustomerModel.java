package com.billing.oppiliappanagency;

public class CustomerModel {




    int Id;
    String Customer_name;
    String Total_amount;
    String Number_of_products;

    public CustomerModel(int id,String customer_name, String total_amount, String number_of_products) {
        Id = id;
        Customer_name = customer_name;
        Total_amount = total_amount;
        Number_of_products = number_of_products;
    }

    @Override
    public String toString() {
        return "CustomerModel{" +
                "Customer_name='" + Customer_name + '\'' +
                ", Total_amount='" + Total_amount + '\'' +
                ", Number_of_products='" + Number_of_products + '\'' +
                '}';
    }
    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }
    public String getCustomer_name() {
        return Customer_name;
    }

    public void setCustomer_name(String customer_name) {
        Customer_name = customer_name;
    }

    public String getTotal_amount() {
        return Total_amount;
    }

    public void setTotal_amount(String total_amount) {
        Total_amount = total_amount;
    }

    public String getNumber_of_products() {
        return Number_of_products;
    }

    public void setNumber_of_products(String number_of_products) {
        Number_of_products = number_of_products;
    }
}
