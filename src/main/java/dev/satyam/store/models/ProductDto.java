package dev.satyam.store.models;

import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

public class ProductDto {
    @NotEmpty(message = "Name is required")
    private String name;

    @NotEmpty(message = "Brand is required")
    private String brand;

    @NotEmpty(message = "Category is required")
    private String category;

    @Min(0)
    private Double price;

    @Size(min = 10, message = "Description should be at least 10 Characters")
    @Size(max = 2000, message = "Description can not exceed 2000 Characters")
    private String description;

    private MultipartFile product_image;

    public @NotEmpty(message = "Name is required") String getName() {
        return name;
    }

    public void setName(@NotEmpty(message = "Name is required") String name) {
        this.name = name;
    }

    public @NotEmpty(message = "Brand is required") String getBrand() {
        return brand;
    }

    public void setBrand(@NotEmpty(message = "Brand is required") String brand) {
        this.brand = brand;
    }

    public @NotEmpty(message = "Category is required") String getCategory() {
        return category;
    }

    public void setCategory(@NotEmpty(message = "Category is required") String category) {
        this.category = category;
    }

    public @Min(0) Double getPrice() {
        return price;
    }

    public void setPrice(@Min(0) Double price) {
        this.price = price;
    }

    public @Size(min = 10, message = "Description should be at least 10 Characters") @Size(max = 2000, message = "Description can not exceed 2000 Characters") String getDescription() {
        return description;
    }

    public void setDescription(@Size(min = 10, message = "Description should be at least 10 Characters") @Size(max = 2000, message = "Description can not exceed 2000 Characters") String description) {
        this.description = description;
    }

    public MultipartFile getProduct_image() {
        return product_image;
    }

    public void setProduct_image(MultipartFile product_image) {
        this.product_image = product_image;
    }
}
