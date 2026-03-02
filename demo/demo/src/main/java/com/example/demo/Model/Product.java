package com.example.demo.Model;

import jakarta.validation.constraints.*;

public class Product {
    @NotBlank(message = "Mã sản phẩm không được để trống")
    private String id;

    @NotBlank(message = "Tên sản phẩm không được để trống")
    private String name;

    @NotNull(message = "Giá không được để trống")
    @Min(value = 1, message = "Giá phải lớn hơn hoặc bằng 1")
    @Max(value = 9999999, message = "Giá phải nhỏ hơn hoặc bằng 9,999,999")
    private Integer price;

    @Size(max = 200, message = "Tên hình ảnh không quá 200 ký tự")
    private String image;

    @NotBlank(message = "Loại danh mục không được để trống")
    private String category;

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getPrice() { return price; }
    public void setPrice(Integer price) { this.price = price; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
}
