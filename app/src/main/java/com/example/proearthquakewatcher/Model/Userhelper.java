package com.example.proearthquakewatcher.Model;

public class Userhelper {
    String name, username, email, phoneNo, password, confirmPassword, sex, getKeyUser, Description ;

    public Userhelper(String name) {
        this.name = name;
    }

    public Userhelper(String name, String username, String email, String phoneNo, String password, String confirmPassword) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.phoneNo = phoneNo;
        this.password = password;
        this.confirmPassword = confirmPassword;

    }

    public Userhelper(String name, String username, String email, String phoneNo, String password, String confirmPassword, String seletedSex) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.phoneNo = phoneNo;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.sex = seletedSex;
        this.getKeyUser = getKeyUser;
    }

    public Userhelper() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getSex(String sex){return  sex; }
    public  void  setSex (String sex){this.sex = sex; }

    public String getGetKeyUser() { return getKeyUser; }

    public String getDescription() { return Description; }
}
