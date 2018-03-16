package com.codesquad.raven.repository;

import android.arch.persistence.room.ColumnInfo;

public class PairModel {

    @ColumnInfo(name = "key_message")
    private String keyMessage;

    @ColumnInfo(name = "value_message")
    private String valueMessage;

    @ColumnInfo(name = "value_type")
    private String valueType;

    public PairModel(String keyMessage, String valueMessage, String valueType) {
        this.keyMessage = keyMessage;
        this.valueMessage = valueMessage;
        this.valueType = valueType;
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

    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }
}
