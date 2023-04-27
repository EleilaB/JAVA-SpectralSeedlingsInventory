package com.example.demo;

import jakarta.persistence.*;

@Entity
public class Users {

    private String username;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    Users(){};

    Users(String username){
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
