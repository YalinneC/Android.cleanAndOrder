package com.example.yalinne.cleanandorder;

/**
 * Created by yalinnecastelan on 4/25/16.
 */
public class User {
    private int id;
    private String name;
    private String email;

    public User(){}

    public User(String name, String email) {
        super();
        this.name = name;
        this.email = email;
    }
    //getters & setters

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() {  return email;  }
    public void setEmail(String author) { this.email = email;}

    @Override
    public String toString() {
        return name;
    }
}
