package com.aldieemaulana.president.model;

import java.io.Serializable;

public class Price implements Serializable {

    public static final String TABLE_NAME = "prices";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_SP = "sp";
    public static final String COLUMN_CP = "cp";


    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_NAME + " TEXT,"
                    + COLUMN_SP + " TEXT,"
                    + COLUMN_CP + " TEXT"
                    + ")";

    private final static long serialVersionUID = -8274647460392051685L;
    private Integer id;
    private String name;
    private String sp;
    private String cp;

    public Price() {
    }

    public Price(Integer id, String name, String sp, String cp) {
        super();
        this.id = id;
        this.name = name;
        this.sp = sp;
        this.cp = cp;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSp() {
        return sp;
    }

    public void setSp(String sp) {
        this.sp = sp;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

}