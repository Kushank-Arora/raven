package com.codesquad.raven.repository;

import android.arch.persistence.room.ColumnInfo;

public class PairModel {

    @ColumnInfo(name = "key_message")
    private String keyMessage;

    @ColumnInfo(name = "value_message")
    private String valueMessage;

    public PairModel(String keyMessage, String valueMessage) {
        this.keyMessage = keyMessage;
        this.valueMessage = valueMessage;
    }

    public PairModel() {
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
}
