package dev.satyam.store.models;

import jakarta.validation.constraints.*;

public class UserDto {

    @NotEmpty(message = "First name is required")
    private String first_name;

    @NotEmpty(message = "Last name is required")
    private String last_name;

    @NotEmpty(message = "Email is required")
    @Email(message = "Email should be valid!")
    private String email;

    @NotEmpty(message = "Gender is required")
    private String gender;

    @NotEmpty(message = "Country is required")
    private String country;

    @Size(min = 10, message = "At least should be 10 chars.")
    @Size(max = 2000, message = "Can not exceed 200 Chars")
    private String bio;

    public @NotEmpty(message = "First name is required") String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(@NotEmpty(message = "First name is required") String first_name) {
        this.first_name = first_name;
    }

    public @NotEmpty(message = "Last name is required") String getLast_name() {
        return last_name;
    }

    public void setLast_name(@NotEmpty(message = "Last name is required") String last_name) {
        this.last_name = last_name;
    }

    public @NotEmpty(message = "Email is required") @Email(message = "Email should be valid!") String getEmail() {
        return email;
    }

    public void setEmail(@NotEmpty(message = "Email is required") @Email(message = "Email should be valid!") String email) {
        this.email = email;
    }

    public @NotEmpty(message = "Gender is required") String getGender() {
        return gender;
    }

    public void setGender(@NotEmpty(message = "Gender is required") String gender) {
        this.gender = gender;
    }

    public @NotEmpty(message = "Country is required") String getCountry() {
        return country;
    }

    public void setCountry(@NotEmpty(message = "Country is required") String country) {
        this.country = country;
    }

    public @Size(min = 10, message = "At least should be 10 chars.") @Size(max = 2000, message = "Can not exceed 200 Chars") String getBio() {
        return bio;
    }

    public void setBio(@Size(min = 10, message = "At least should be 10 chars.") @Size(max = 2000, message = "Can not exceed 200 Chars") String bio) {
        this.bio = bio;
    }
}
