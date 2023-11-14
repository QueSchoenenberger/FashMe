package com.lepquold.model;

import java.io.Serializable;

public class Type implements Serializable {
    private String name;
    private BodyParts bodyPart;

    public Type(String name, BodyParts bodyPart) {
        this.name = name;
        this.bodyPart = bodyPart;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BodyParts getBodyPart() {
        return bodyPart;
    }

}
