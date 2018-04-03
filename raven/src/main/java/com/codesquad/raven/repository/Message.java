package com.codesquad.raven.repository;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "raven_message")
public class Message {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "who")
    private String who;

    @ColumnInfo(name = "whom")
    private String whom;

    @ColumnInfo(name = "key_message")
    private String keyMessage;

    @ColumnInfo(name = "value_message")
    private String valueMessage;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public String getWhom() {
        return whom;
    }

    public void setWhom(String whom) {
        this.whom = whom;
    }

    public String getKeyMessage() {
        return keyMessage;
    }

    public void setKeyMessage(String keyMessage) {
        this.keyMessage = keyMessage;
    }

    public String getValueMessage() {
        return valueMessage;
    }

    public void setValueMessage(String valueMessage) {
        this.valueMessage = valueMessage;
    }

    public Message(String who, String whom, String keyMessage, String valueMessage) {
        this.who = who;
        this.whom = whom;
        this.keyMessage = keyMessage;
        this.valueMessage = valueMessage;
    }

    public Message() {
    }
}
