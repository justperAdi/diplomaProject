package com.example.backend.controllers;

import com.example.backend.exceptions.ServiceException;
import com.example.backend.models.entities.Todo;
import com.example.backend.models.entities.User;
import com.example.backend.services.TodoService;
import com.example.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping(value = "/api/todos")
public class TodoRestController {

    private TodoService todoService;
    private UserService userService;

    @Autowired
    public TodoRestController(TodoService todoService, UserService userService){
        this.todoService = todoService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(todoService.findAll(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id) {
        return new ResponseEntity<>(todoService.findById(id), HttpStatus.OK);
    }


    @GetMapping("findAllByCurrentDateAndUserId")
    public ResponseEntity<?> getAllCurrentDay(Authentication authentication) {
        User user = userService.findByLogin(authentication.getName());
        return new ResponseEntity<>(todoService.findAllByCurrentAt(new Date(), user.getId()), HttpStatus.OK);
    }
    @GetMapping("findAllByUserId")
    public ResponseEntity<?> getAll(Authentication authentication) {
        User user = userService.findByLogin(authentication.getName());
        return new ResponseEntity<>(todoService.findAllByUserId(user.getId()), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Todo todo,Authentication authentication) {
        if (todo.getId() != null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        User user = userService.findByLogin(authentication.getName());
        todo.setUser(user);
        todoService.save(todo);
        return new ResponseEntity<>(todo, HttpStatus.CREATED);
    }
    @GetMapping("getAllTodoByDay")
    public ResponseEntity<?> getAllProductsSearch(@RequestParam("currentAt") String currentAt , Authentication authentication) throws ServiceException, ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = formatter.parse(currentAt);
        System.out.println(date);
        User user = userService.findByLogin(authentication.getName());
        return new ResponseEntity<>(todoService.findAllByCurrentAt(date, user.getId()), HttpStatus.OK);
    }

    @RequestMapping(value = "{id}", method = {RequestMethod.PUT, RequestMethod.PATCH})
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Todo todo) {
        Todo todoOpt = todoService.findById(id);
        if (todo.getId() == null || ! todo.getId().equals(id)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(todoService.update(todo), HttpStatus.OK);
    }


    @DeleteMapping(value = "{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        todoService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
