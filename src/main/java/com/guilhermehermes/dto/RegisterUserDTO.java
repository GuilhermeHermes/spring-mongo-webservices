package com.guilhermehermes.dto;

import com.guilhermehermes.domain.User;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.*;

public class RegisterUserDTO {
    @Id
    private String id;

    @NotNull(message = "The full name is required.")
    @NotEmpty(message = "The email address is required.")
    @Size(min = 2, max = 100, message = "The length of full name must be between 2 and 100 characters.")
    private String name;

    @NotNull(message = "The email address is required.")
    @NotEmpty(message = "The email address is required.")
    @Email(message = "The email address is invalid.", flags = { Pattern.Flag.CASE_INSENSITIVE })
    private String email;

    public RegisterUserDTO(){

    }

    public RegisterUserDTO(User user){
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User toUser() {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        return user;
    }
}
