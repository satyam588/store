package dev.satyam.store.models;

import jakarta.validation.constraints.NotEmpty;

public class LoginDto {
    @NotEmpty(message = "Email is required!")
    private String email;

    @NotEmpty(message = "Password is required!")
    private String password;

    public @NotEmpty(message = "Email is required!") String getEmail() {
        return email;
    }

    public void setEmail(@NotEmpty(message = "Email is required!") String email) {
        this.email = email;
    }

    public @NotEmpty(message = "Password is required!") String getPassword() {
        return password;
    }

    public void setPassword(@NotEmpty(message = "Password is required!") String password) {
        this.password = password;
    }
}
