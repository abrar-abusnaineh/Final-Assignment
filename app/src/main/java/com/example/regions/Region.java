package com.example.regions;

public class Region {
    int id;
    String name;
    String image_id;


    public Region(int id, String name, String image_id) {
        this.id = id;
        this.name = name;
        this.image_id = image_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage_id() {
        return image_id;
    }

    public void setImage_id(String image_id) {
        this.image_id = image_id;
    }

}
