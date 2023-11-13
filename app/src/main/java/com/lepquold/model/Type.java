package com.lepquold.model;

import java.io.Serializable;

public class Type implements Serializable {
    private int typeID;
    public String name;
    public BodyParts bodyPart;

    public Type(String name, BodyParts bodyPart) {
        this.name = name;
        this.bodyPart = bodyPart;
    }
}
