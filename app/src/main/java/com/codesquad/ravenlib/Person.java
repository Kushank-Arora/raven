package com.codesquad.ravenlib;

public class Person {
    String name, moreData;

    public Person(String name, String moreData) {
        this.name = name;
        this.moreData = moreData;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMoreData() {
        return moreData;
    }

    public void setMoreData(String moreData) {
        this.moreData = moreData;
    }
}
