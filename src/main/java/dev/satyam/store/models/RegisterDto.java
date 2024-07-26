package dev.satyam.store.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public class RegisterDto {
    @NotEmpty(message = "User Name is required")
    private String userName;

    @NotEmpty(message = "Email is Required")
    @Email(message = "Email should be valid")
    private String email;

    @NotEmpty(message = "Password is required")
    private String password;

    public @NotEmpty(message = "Confirm Password is required") String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(@NotEmpty(message = "Confirm Password is required") String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @NotEmpty(message = "Confirm Password is required")
    private String confirmPassword;

    public @NotEmpty(message = "User Name is required") String getUserName() {
        return userName;
    }

    public void setUserName(@NotEmpty(message = "User Name is required") String userName) {
        this.userName = userName;
    }

    public @NotEmpty(message = "Email is Required") @Email(message = "Email should be valid") String getEmail() {
        return email;
    }

    public void setEmail(@NotEmpty(message = "Email is Required") @Email(message = "Email should be valid") String email) {
        this.email = email;
    }

    public @NotEmpty(message = "Password is required") String getPassword() {
        return password;
    }

    public void setPassword(@NotEmpty(message = "Password is required") String password) {
        this.password = password;
    }
}
