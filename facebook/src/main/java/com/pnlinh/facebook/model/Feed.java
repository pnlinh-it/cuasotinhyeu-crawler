package com.pnlinh.facebook.model;

public class Feed {
    public String id;
    public String name;
    public String type;

    @Override
    public String toString() {
        return "Feed{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
