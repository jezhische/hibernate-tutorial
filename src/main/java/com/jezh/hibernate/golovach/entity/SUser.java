package com.jezh.hibernate.golovach.entity;

public class SUser {
    private int id;
    private String login;
    private String name;
    private String email;

    public SUser() {
    }

    public SUser(String login, String name, String email) {
        this.login = login;
        this.name = name;
        this.email = email;
    }

    public SUser(int id, String login, String name, String email) {
        this.id = id;
        this.login = login;
        this.name = name;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

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
}
