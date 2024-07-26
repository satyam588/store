package dev.satyam.store.models;

import jakarta.validation.constraints.*;

import java.util.Date;

public class TodoDto {
    @NotEmpty(message = "Title is required!")
    private String title;

    @NotEmpty(message = "Description is required!")
    private String description;

    @NotNull(message = "Due date is required!")
    private String due_date;

    public @NotEmpty(message = "Title is required!") String getTitle() {
        return title;
    }

    public void setTitle(@NotEmpty(message = "Title is required!") String title) {
        this.title = title;
    }

    public @NotEmpty(message = "Description is required!") String getDescription() {
        return description;
    }

    public void setDescription(@NotEmpty(message = "Description is required!") String description) {
        this.description = description;
    }

    public String getDue_date() {
        return due_date;
    }

    public void setDue_date(@NotEmpty(message = "Due date is required!") String due_date) {
        this.due_date = due_date;
    }
}
