package com.javamaster.demo.db;

public class Phones {
    public static final String TABLE_NAME = "phones";
    public static final String ID = "id";
    public static final String PHONE_NUM = "phone_number";
    public static final String DB_NAME = "demo.db";
    public static final int DB_VERSION = 1;
    public static final String TABLE_STRUCTURE = "CREATE TABLE IF NOT EXISTS " +
             TABLE_NAME + " (" + ID + " INTEGER PRIMARY KEY, " + PHONE_NUM + " TEXT)";
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

}
