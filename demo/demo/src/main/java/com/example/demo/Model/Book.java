package com.example.demo.Model;

public class Book {
    private int id;
    private String title;
    private String author;

    // Constructor không tham số (cần cho Thymeleaf form binding)
    public Book() {
    }

    // Constructor với tham số
    public Book(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
}