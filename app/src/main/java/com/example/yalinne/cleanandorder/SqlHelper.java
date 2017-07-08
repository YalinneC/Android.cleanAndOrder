package com.example.yalinne.cleanandorder;

import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;



/**
 * Created by yalinnecastelan on 4/25/16.
 */
public class SqlHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 2;
    // Database Name
    private static final String DATABASE_NAME = "CleanAndOrderDB";

    // Group table name
    private static final String TABLE_GROUP = "theGroup";
    // Group Table Columns names
    private static final String KEY_GROUPNAME = "name";

    // Users table name
    private static final String TABLE_USERS = "users";
    // Users Table Columns names
    private static final String KEY_USERID = "id";
    private static final String KEY_USERNAME = "name";
    private static final String KEY_EMAIL = "email";

    // Rooms table name
    private static final String TABLE_ROOMS = "rooms";
    // Rooms Table Columns names
    private static final String KEY_ROOMID = "id";
    private static final String KEY_ROOMNAME = "name";

    // Chores table name
    private static final String TABLE_CHORES = "chores";
    // Chores Table Columns names
    private static final String KEY_CHOREID = "id";
    private static final String KEY_CHORENAME = "name";
    private static final String KEY_ASSIGNEDUSERID = "userId";
    private static final String KEY_ROOMTOCLEANID = "roomId";

    public SqlHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        // SQL statement to create users table
        String CREATE_TABLE = "CREATE TABLE theGroup ( " +
                "name TEXT)";
        // create user table
        db.execSQL(CREATE_TABLE);

        // SQL statement to create users table
        CREATE_TABLE = "CREATE TABLE users ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, "+
                "email TEXT )";
        // create user table
        db.execSQL(CREATE_TABLE);
        // Inserting 4 dummy users into table which will henceforth be only updated.
        String DUMMY_INSERT;
        DUMMY_INSERT = "INSERT INTO users VALUES (0, \"\",\"\")";
        db.execSQL(DUMMY_INSERT);
        DUMMY_INSERT = "INSERT INTO users VALUES (1, \"\",\"\")";
        db.execSQL(DUMMY_INSERT);
        DUMMY_INSERT = "INSERT INTO users VALUES (2, \"\",\"\")";
        db.execSQL(DUMMY_INSERT);
        DUMMY_INSERT = "INSERT INTO users VALUES (3, \"\",\"\")";
        db.execSQL(DUMMY_INSERT);

        // SQL statement to create rooms table
        CREATE_TABLE = "CREATE TABLE rooms ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT )";
        // create books table
        db.execSQL(CREATE_TABLE);

        // SQL statement to create chores table
        CREATE_TABLE = "CREATE TABLE chores ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "userId INTEGER, " +
                "roomId INTEGER )";
        // create chores table
        db.execSQL(CREATE_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older tables if existed
        db.execSQL("DROP TABLE IF EXISTS theGroup");
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS rooms");
        db.execSQL("DROP TABLE IF EXISTS chores");

        // create fresh tables
        this.onCreate(db);
    }

    /*CRUD operations (create "add", read "get", update, delete) */
    public void addGroup(String groupName){
        Log.d("addGroup", groupName);
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_GROUPNAME, groupName); // set username

        // 3. insert
        db.insert(TABLE_GROUP, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/values

        // 4. Close dbase
        db.close();
    }

    // Updating single user
    public int insertOrUpdateGroup(String groupName) {
        Log.d("insertOrUpdateGroup", groupName);
        String oldGroupName = getGroup();

        if (oldGroupName.matches(groupName)) {
            return 0;
        }
        int i = 0;

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_GROUPNAME, groupName); // set group name

        if (oldGroupName.contentEquals("")) {
            // 3. insert
            db.insert(TABLE_GROUP, // table
                    null, //nullColumnHack
                    values); // key/value -> keys = column names/values
        }
        else {
            // 3. updating row
            i = db.update(TABLE_GROUP, //table
                    values, // column/value
                    null, // selections
                    null); //selection args
        }

        // 4. Close dbase
        db.close();

        Log.d("InsertOrUpdateGroup", groupName);
        return i;
    }

    // Get group name
    public String getGroup() {
        String groupName = "";

        // 1. build the query
        String query = "SELECT name FROM " + TABLE_GROUP;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build user and add it to list
        if (cursor.moveToFirst()) {
            groupName = cursor.getString(0);
        }


        Log.d("getGroup()", groupName);

        return groupName; // return groupName
    }

    public void addUser(User user){
        Log.d("addUser", user.toString());
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME, user.getName()); // set username
        values.put(KEY_EMAIL, user.getEmail()); // set email

        // 3. insert
        db.insert(TABLE_USERS, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/values

        // 4. Close dbase
        db.close();
    }

    // Get All Users
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>(4);

        // 1. build the query
        String query = "SELECT * FROM " + TABLE_USERS;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build user and add it to list
        User user = null;
        if (cursor.moveToFirst()) {
            do {
                user = new User();
                user.setId(Integer.parseInt(cursor.getString(0)));
                user.setName(cursor.getString(1));
                user.setEmail(cursor.getString(2));

                // Add user to users
                users.add(user);
            } while (cursor.moveToNext());
        }

        Log.d("getAllUsers()", users.toString());

        return users; // return users
    }
    // Updating single user
    public int updateUser(User user) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put("name", user.getName()); // get title
        values.put("email", user.getEmail()); // get author

        // 3. updating row
        int i = db.update(TABLE_USERS, //table
                values, // column/value
                KEY_USERID+" = ?", // selections
                new String[] { String.valueOf(user.getId()) }); //selection args
        // 4. close dbase
        db.close();
        Log.d("UpdateUser", user.toString());
        return i;

    }
    // Deleting single user
    public void deleteUser(User user) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(TABLE_USERS,
                KEY_USERID+" = ?",
                new String[] { String.valueOf(user.getId()) });

        // 3. close
        db.close();

        Log.d("deleteUser", user.toString());
    }





    public void addRoom(Room room){
        Log.d("addRoom", room.toString());
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_ROOMNAME, room.getName()); // get chore name

        // 3. insert
        db.insert(TABLE_ROOMS, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/values

        // 4. Close dbase
        db.close();
    }

    // Get All Rooms
    public List<Room> getAllRooms() {
        List<Room> rooms = new LinkedList<Room>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_ROOMS;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build user and add it to list
        Room room = null;
        if (cursor.moveToFirst()) {
            do {
                room = new Room();
                room.setId(Integer.parseInt(cursor.getString(0)));
                room.setName(cursor.getString(1));

                // Add room to rooms
                rooms.add(room);
            } while (cursor.moveToNext());
        }

        Log.d("getAllRooms()", rooms.toString());

        return rooms; // return rooms
    }
    // Updating single room
    public int updateRoom(Room room, String newName) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put("name", newName); // set title

        // 3. updating row
        int i = db.update(TABLE_ROOMS, //table
                values, // column/value
                KEY_ROOMID+" = ?", // selections
                new String[] { String.valueOf(room.getId()) }); //selection args
        // 4. close dbase
        db.close();
        Log.d("UpdateRoom", room.toString());
        return i;

    }
    // Deleting single room
    public void deleteRoom(Room room) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(TABLE_ROOMS,
                KEY_ROOMID+" = ?",
                new String[] { String.valueOf(room.getId()) });

        // 3. close
        db.close();

        Log.d("deleteRoom", room.toString());
    }





    public void addChore(Chore chore){
        Log.d("addChore", chore.toString());
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_CHORENAME, chore.getName()); // set name
        values.put(KEY_ASSIGNEDUSERID, chore.getUserId()); // set user id
        values.put(KEY_ROOMTOCLEANID, chore.getRoomId()); // set room id

        // 3. insert
        db.insert(TABLE_CHORES, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/values

        // 4. Close dbase
        db.close();
    }

    // Get All Chores
    public List<Chore> getAllChores() {
        List<Chore> chores = new LinkedList<Chore>();

        // 1. build the query
        String query = "SELECT * FROM " + TABLE_CHORES;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build user and add it to list
        Chore chore = null;
        if (cursor.moveToFirst()) {
            do {
                chore = new Chore();
                chore.setId(Integer.parseInt(cursor.getString(0)));
                chore.setName(cursor.getString(1));
                chore.setUserId(cursor.getInt(2));
                chore.setRoomId(cursor.getInt(3));

                // Add room to rooms
                chores.add(chore);
            } while (cursor.moveToNext());
        }

        Log.d("getAllRooms()", chores.toString());

        return chores; // return rooms
    }
    // Get All Chores
    public List<Chore> getChores(Integer roomId) {
        List<Chore> chores = new LinkedList<Chore>();

        // 1. build the query
        String query = "SELECT * FROM " + TABLE_CHORES + " WHERE roomId=" + roomId;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build user and add it to list
        Chore chore = null;
        if (cursor.moveToFirst()) {
            do {
                chore = new Chore();
                chore.setId(Integer.parseInt(cursor.getString(0)));
                chore.setName(cursor.getString(1));
                chore.setUserId(cursor.getInt(2));
                chore.setRoomId(cursor.getInt(3));

                // Add room to rooms
                chores.add(chore);
            } while (cursor.moveToNext());
        }

        Log.d("getChores()", chores.toString());

        return chores; // return rooms
    }


    // Get All Chores for a particular user
    public List<Chore> getChoresOfUser(Integer userId) {
        List<Chore> chores = new LinkedList<Chore>();

        // 1. build the query
        String query = "SELECT * FROM " + TABLE_CHORES + " WHERE userId=" + userId;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build user and add it to list
        Chore chore = null;
        if (cursor.moveToFirst()) {
            do {
                chore = new Chore();
                chore.setId(Integer.parseInt(cursor.getString(0)));
                chore.setName(cursor.getString(1));
                chore.setUserId(cursor.getInt(2));
                chore.setRoomId(cursor.getInt(3));

                // Add room to rooms
                chores.add(chore);
            } while (cursor.moveToNext());
        }

        Log.d("getChoresOfUser()", chores.toString());

        return chores; // return rooms
    }

    // Updating single chore
    public int updateChore(Chore chore, String newName, Integer newUserId, Integer newRoomId) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put("name", newName); // set name
        values.put("userId", newUserId); // set userId
        values.put("roomId", newRoomId); // set roomId

        // 3. updating row
        int i = db.update(TABLE_CHORES, //table
                values, // column/value
                KEY_CHOREID+" = ?", // selections
                new String[] { String.valueOf(chore.getId()) }); //selection args
        // 4. close dbase
        db.close();
        Log.d("UpdateChore", chore.toString());
        return i;
    }

    // Updating single chore
    public int updateChoreUser(Integer choreId, Integer newUserId) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put("userId", newUserId); // set userId

        // 3. updating row
        int i = db.update(TABLE_CHORES, //table
                values, // column/value
                KEY_CHOREID+" = ?", // selections
                new String[] { choreId.toString() }); //selection args
        // 4. close dbase
        db.close();

        Log.d("UpdateChoreUser", choreId.toString());
        return i;
    }

    // Deleting single room
    public void deleteChore(Integer Id) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(TABLE_CHORES,
                KEY_CHOREID+" = ?",
                new String[] { String.valueOf(Id) });

        // 3. close
        db.close();

        Log.d("deleteChore Id=", Id.toString());
    }

}
