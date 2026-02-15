package com._project.springboot_backend.DTO;



//global_text表对应的dto类
public class DtoGlobal_text {


    String Username;
    String Telephone;
    String Remarks;
    String Address;
    String totals;
    String back_img;
    String head_img;

    // 无参构造
    public DtoGlobal_text() {}
    //get和set
    public String getUsername() {
        return Username;
    }
    public void setUsername(String un) {
        this.Username = un;
    }
    public String getTelephone() {
        return Telephone;
    }
    public void setTelephone(String telephone) {
        this.Telephone = telephone;
    }
    public String getRemarks() {
        return Remarks;
    }
    public void setRemarks(String remarks) {
        this.Remarks = remarks;
    }
    public String getAddress() {
        return Address;
    }
    public void setAddress(String address) {
        this.Address = address;
    }
    public String getTotals() {
        return totals;
    }
    public void setTotals(String totals) {
        this.totals = totals;
    }
    public String getBack_img() {
        return back_img;
    }
    public void setBack_img(String back_img) {
        this.back_img = back_img;
    }
    public String getHead_img() {
        return head_img;
    }
    public void setHead_img(String head_img) {
        this.head_img = head_img;
    }





}
