package com.example.bookkeepingapp.Database;

import org.litepal.crud.LitePalSupport;

public class DataIn extends LitePalSupport {
    private String money;

    private String account;
    private String bill;
    private String account_in;
    private String account_out;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private int second;

    private String category_1;
    private String category_2;
    private String member;
    private String remark;

    public String getBill() {
        return bill;
    }

    public void setBill(String bill) {
        this.bill = bill;
    }

    public String getAccount_in() {
        return account_in;
    }

    public void setAccount_in(String account_in) {
        this.account_in = account_in;
    }

    public String getAccount_out() {
        return account_out;
    }

    public void setAccount_out(String account_out) {
        this.account_out = account_out;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
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

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public String getCategory_1() {
        return category_1;
    }

    public void setCategory_1(String category_1) {
        this.category_1 = category_1;
    }

    public String getCategory_2() {
        return category_2;
    }

    public void setCategory_2(String category_2) {
        this.category_2 = category_2;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remake) {
        this.remark = remark;
    }
}
