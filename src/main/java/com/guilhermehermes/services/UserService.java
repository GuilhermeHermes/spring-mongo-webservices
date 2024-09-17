package com.guilhermehermes.services;


import com.guilhermehermes.domain.User;
import com.guilhermehermes.dto.RegisterUserDTO;
import com.guilhermehermes.repository.UserRepository;
import com.guilhermehermes.services.exception.ObjectNotFoundException;
import com.guilhermehermes.services.exception.UserValidateException;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Validated
public class UserService {

    @Autowired
    private UserRepository repo;

    public List<User> findAll(){
        return repo.findAll();
    }

    public User findByID(String id){
        Optional<User> user = repo.findById(id);
        return user.orElseThrow(() -> new ObjectNotFoundException(id));
    }



    public User insert(@Valid RegisterUserDTO registerUserDTO){
        validate(registerUserDTO.toUser());

        User user = new User();
        user.setName(registerUserDTO.getName());
        user.setEmail(registerUserDTO.getEmail());


        try {
            return repo.insert(user);
        } catch (Exception e) {
            throw new UserValidateException("Validation failed: " + e.getMessage());
        }
    }

    public void delete(String id){
        findByID(id);
        repo.deleteById(id);
    }

    public User update(String id, User user){
        validate(user);
        User newUser = findByID(id);
        newUser.setName(user.getName());
        newUser.setEmail(user.getEmail());
        return repo.save(newUser);
    }

    public void validate(User user){
        List<String> validationErrors = new ArrayList<>();

        // Validação manual dos campos
        if (user.getName() == null || user.getName().trim().isEmpty()) {
            validationErrors.add("Name is required");
        } else if (user.getName().length() < 2 || user.getName().length() > 100) {
            validationErrors.add("Name must be between 2 and 100 characters");
        }

        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            validationErrors.add("Email is required");
        } else if (!isValidEmail(user.getEmail())) {
            validationErrors.add("Invalid email format");
        }

        if (!validationErrors.isEmpty()) {
            throw new UserValidateException("Validation failed: " + String.join(", ", validationErrors));
        }
    }
    private boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }
}
