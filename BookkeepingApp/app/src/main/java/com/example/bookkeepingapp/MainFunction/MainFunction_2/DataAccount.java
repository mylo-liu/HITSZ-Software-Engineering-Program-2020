package com.example.bookkeepingapp.MainFunction.MainFunction_2;

public class DataAccount {
    private String account;
    private String money_in;
    private String money_out;
    private String money_left;
    private int year;
    private int month;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getMoney_in() {
        return money_in;
    }

    public void setMoney_in(String money_in) {
        this.money_in = money_in;
    }

    public String getMoney_out() {
        return money_out;
    }

    public void setMoney_out(String money_out) {
        this.money_out = money_out;
    }

    public String getMoney_left(){
        return money_left;
    }
    public void setMoney_left(String money_left){
        this.money_left = money_left;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }
}
