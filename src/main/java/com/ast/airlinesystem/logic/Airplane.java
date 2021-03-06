package com.ast.airlinesystem.logic;

public class Airplane {

    private String id;
    private AirplaneType type;

    public Airplane(String id, AirplaneType type) {
        this.id = id;
        this.type = type;
    }
    public Airplane(String id) {
        this.id = id;
        this.type = null;
    }
    public Airplane() {
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AirplaneType getType() {
        return type;
    }

    public void setType(AirplaneType type) {
        this.type = type;
    }


}
