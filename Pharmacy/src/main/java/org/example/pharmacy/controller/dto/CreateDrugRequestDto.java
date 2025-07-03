package org.example.pharmacy.controller.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public class CreateDrugRequestDto {
    @Size(min = 3, max = 50, message = "name should have between 3 and 50 characters")
    private String name;
    @Min(value = 0, message = "price cant be negative")
    private float price;
    private String description;
    @Min(value = 0, message = "stock cant be negative")
    private int stock;

    public CreateDrugRequestDto(String name, float price, String description, int stock) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.stock = stock;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
