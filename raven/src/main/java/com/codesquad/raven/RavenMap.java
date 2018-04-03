package com.codesquad.raven;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class RavenMap {
    private Map<String, String> map;

    RavenMap(){
        map = new HashMap<>();
    }

    public void put(String key, String value) {
        map.put(key, value);
    }

    public<T> T get(String key, TypeToken<T> type) {
        return (new Gson()).fromJson(map.get(key), type.getType());
    }

    public<T> T get(String key, Class<T> theClass) {
        return (new Gson()).fromJson(map.get(key), theClass);
    }
}
