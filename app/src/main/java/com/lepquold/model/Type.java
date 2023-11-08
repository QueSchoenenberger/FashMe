package com.lepquold.model;

public class Type {
    private int typeID;
    public String name;
    public BodyParts bodyPart;

    public Type(String name, BodyParts bodyPart) {
        this.name = name;
        this.bodyPart = bodyPart;
    }
}
