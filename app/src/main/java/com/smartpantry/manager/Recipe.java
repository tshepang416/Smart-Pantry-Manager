package com.smartpantry.manager;

public class Recipe {
    private long id;
    private String name;
    private String ingredients;
    private String method;

    public Recipe() {
    }

    public Recipe(String name, String ingredients, String method) {
        this.name = name;
        this.ingredients = ingredients;
        this.method = method;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
