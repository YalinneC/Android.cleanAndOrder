package com.example.yalinne.cleanandorder;

/**
 * Created by yalinnecastelan on 4/25/16.
 */
public class Room {
    private int id;
    private String name;

    public Room(){}

    public Room(String name) {
        super();
        this.name = name;
    }

    //getters & setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + "]";
    }
}
