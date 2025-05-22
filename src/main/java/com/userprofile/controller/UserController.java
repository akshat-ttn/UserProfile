package com.userprofile.controller;

import com.userprofile.dto.GenericResponseDTO;
import com.userprofile.dto.UserDTO;
import com.userprofile.dto.UserUpdateDTO;
import com.userprofile.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final MessageSource messageSource;


    @GetMapping("")
    public ResponseEntity<List<UserDTO>> getAllUsers(
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "0") int pageOffset,
            @RequestParam(defaultValue = "createdAt") String sort,
            @RequestParam(defaultValue = "asc") String direction,
            @RequestParam(required = false) String email
    ) {
        return ResponseEntity.ok(userService.getAllUsers(pageSize, pageOffset, sort,direction,email));
    }

    @PostMapping()
    public ResponseEntity<GenericResponseDTO> saveUser(@Valid @RequestBody UserDTO userDTO){
        userService.saveUser(userDTO);
        return new ResponseEntity<>(new GenericResponseDTO(true, messageSource.getMessage("user.created.success",null,LocaleContextHolder.getLocale())), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenericResponseDTO> updateUser(@Valid @RequestBody UserUpdateDTO userDTO, @PathVariable String id){
        userService.updateUser(id,userDTO);
        return ResponseEntity.ok(new GenericResponseDTO(true, messageSource.getMessage("user.profile.updated", null,LocaleContextHolder.getLocale())));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponseDTO> deleteUser(@PathVariable String id){
        userService.deleteUser(id);
        return ResponseEntity.ok(new GenericResponseDTO(true, messageSource.getMessage("user.deleted.success", null, LocaleContextHolder.getLocale())));

    }






}
