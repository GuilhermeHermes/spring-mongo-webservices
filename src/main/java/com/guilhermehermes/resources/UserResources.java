package com.guilhermehermes.resources;

import com.guilhermehermes.domain.User;
import com.guilhermehermes.dto.RegisterUserDTO;
import com.guilhermehermes.dto.ResponseUserDTO;
import com.guilhermehermes.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value="/users")
public class UserResources {

    @Autowired
    private UserService service;

    @GetMapping
    public ResponseEntity<List<ResponseUserDTO>> findAll() {

        List<User> list = service.findAll();
        List<ResponseUserDTO> listDto = list.stream().map(x -> new ResponseUserDTO(x)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDto);
    }

    @GetMapping(value="/{id}")
    public ResponseEntity<ResponseUserDTO> findById(@PathVariable String id) {
        User obj = service.findByID(id);
        ResponseUserDTO objDto = new ResponseUserDTO(obj);
        return ResponseEntity.ok().body(objDto);
    }

    @PostMapping
    public ResponseEntity<User> insert(@Valid @RequestBody RegisterUserDTO userDto){
        User user = userDto.toUser();

            user = service.insert(userDto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();
        return ResponseEntity.created(uri).body(user);
    }

    @DeleteMapping(value="/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value="/{id}")
    public ResponseEntity<User> update(@PathVariable String id,@RequestBody User user){
        user = service.update(id,user);
        return ResponseEntity.ok().body(user);
    }
}
