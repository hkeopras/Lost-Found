package com.example.henri.lostandfound;

public class Matches {

    private int id;
    private String category;
    private String description;

    //Constructor
    public Matches(int id, String category, String description) {
        this.id = id;
        this.category = category;
        this.description = description;
    }

    //Setter, getter


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
