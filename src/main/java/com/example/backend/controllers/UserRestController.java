package com.example.backend.controllers;

import com.example.backend.exceptions.ServiceException;
import com.example.backend.models.entities.Role;
import com.example.backend.models.entities.User;
import com.example.backend.services.UserService;
import com.example.backend.shared.utils.codes.ErrorCode;
import com.example.backend.shared.utils.responses.ErrorResponse;
import com.example.backend.shared.utils.responses.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/users")
public class UserRestController {

    private UserService userService;

    @Autowired
    public UserRestController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }
    @RequestMapping(path = "{id}" , method = {RequestMethod.PATCH, RequestMethod.PUT})
    public ResponseEntity<?> update(@RequestBody User user, @PathVariable Long id) throws ServiceException {
        if(user.getId().equals(id)){
            User newUser = userService.update(user);
            return new ResponseEntity<>(SuccessResponse.builder()
                    .message("updated")
                    .data(newUser)
                    .build(), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
    @PostMapping
    public ResponseEntity<?> add(@RequestBody User user) throws ServiceException {
        User currentUser = userService.findByLogin(user.getLogin());
        if (currentUser != null){
            return new ResponseEntity<>(ErrorResponse.builder()
            .errorCode(ErrorCode.SYSTEM_ERROR)
            .message("login exists")
            .build(), HttpStatus.BAD_REQUEST);
        }
        Role role = new Role();
        role.setId(Role.ROLE_USER_ID);
        user.setRole(role);
        return new ResponseEntity<>(userService.save(user), HttpStatus.OK);
    }
    @PostMapping("addAdmin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> addAdmin(@RequestBody User user) throws ServiceException {
        if (userService.findByLogin(user.getLogin()) != null){
            return new ResponseEntity<>(ErrorResponse.builder()
                    .errorCode(ErrorCode.SYSTEM_ERROR)
                    .message("login exists")
                    .build(), HttpStatus.BAD_REQUEST);
        }
        Role role = new Role();
        role.setId(Role.ROLE_ADMIN_ID);
        user.setRole(role);
        return new ResponseEntity<>(userService.save(user), HttpStatus.OK);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) throws ServiceException {
        userService.deleteById(id);
        return new ResponseEntity<>(SuccessResponse.builder().message("deleted").build(), HttpStatus.OK);
    }

    @PostMapping("/current")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) throws ServiceException {
        String login = authentication.getName();
        User user = userService.findByLogin(login);
        return new ResponseEntity<>(SuccessResponse.builder()
                .message("found")
                .data(user)
                .build(), HttpStatus.OK);
    }
}
