package com.sourav.schooldetails.helpers;

import java.io.Serializable;

/**
 * Created by sourav on 6/7/16.
 */
public class StudentBean implements Serializable {

    String name;
    String email;
    String mobile;
    String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
