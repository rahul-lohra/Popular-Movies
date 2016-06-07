package com.rahul.popularmovies.Utility;

/**
 * Created by rahulkumarlohra on 07/06/16.
 */
public class Trailer {
    String name,key;

    public Trailer(String key, String name) {
        this.key = key;
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
