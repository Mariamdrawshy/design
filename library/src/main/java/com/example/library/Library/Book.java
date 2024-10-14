package com.example.library.Library;

import javafx.beans.property.SimpleBooleanProperty;

public class Book implements Cloneable {
    private String name;
    private String author;
    private int yearOfPublication;
    private boolean isAvailable;

    // Constructor
    public Book(String name, String author, int yearOfPublication) {
        this.name = name;
        this.author = author;
        this.yearOfPublication = yearOfPublication;
        this.isAvailable = true;
    }

    public Book(String name, String author, int yearOfPublication,boolean isAvailable) {
        this.name = name;
        this.author = author;
        this.yearOfPublication = yearOfPublication;
        this.isAvailable = true;
    }

    @Override
    public Book clone() {
        try {
            return (Book) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(); // Can't happen
        }
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYearOfPublication() {
        return yearOfPublication;
    }

    public void setYearOfPublication(int yearOfPublication) {
        this.yearOfPublication = yearOfPublication;
    }

    public boolean isAvailable() {
        return this.isAvailable;
    }

    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }
}