package com.example.preetha_seyasofttask;

import java.io.Serializable;

public class ContactList implements Serializable {
    private String name;
    private String email;
    private String mobile;
    private String image;

    public ContactList(String name, String email, String mobile, String image) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.image = image;

    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public String getImage() {
        return image;
    }

}
