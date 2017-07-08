package com.example.yalinne.cleanandorder;

/**
 * Created by yalinnecastelan on 4/25/16.
 */
public class Chore {
    private int id;
    private String name;
    private int userId;
    private int roomId;

    public Chore(){}

    public Chore(String name, Integer userId, Integer roomId) {
        super();
        this.name = name;
        this.userId = userId;
        this.roomId = roomId;
    }

    //getters & setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Integer getUserId() {  return userId;  }
    public void setUserId(Integer userId) { this.userId = userId;}
    public Integer getRoomId() {  return roomId;  }
    public void setRoomId(Integer roomId) { this.roomId = roomId;}

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", userId=" + userId + ", roomId=" + roomId + "]";
    }
}
