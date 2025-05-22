package com.userprofile.controller;

import com.userprofile.dto.GenericResponseDTO;
import com.userprofile.dto.UserDTO;
import com.userprofile.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final MessageSource messageSource;


    @PostMapping()
    public ResponseEntity<GenericResponseDTO> saveUser(@Valid @RequestBody UserDTO userDTO){
        userService.saveUser(userDTO);
        return new ResponseEntity<>(new GenericResponseDTO(true, messageSource.getMessage("user.created.success",null,LocaleContextHolder.getLocale())), HttpStatus.CREATED);
    }


}
