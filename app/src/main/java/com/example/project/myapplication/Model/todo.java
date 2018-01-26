package com.example.project.myapplication.Model;

public class todo {

    private String item;

    public todo(String item) {
        this.item = item;

    }

    public todo(){

        this.item = null;

    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }
}
