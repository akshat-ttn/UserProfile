package com.userprofile.controller;

import com.userprofile.dto.GenericResponseDTO;
import com.userprofile.dto.HobbyDTO;
import com.userprofile.service.HobbyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("users/hobby")
public class HobbyController {

    private final HobbyService hobbyService;
    public final MessageSource messageSource;

    @PostMapping("/{userId}")
    public ResponseEntity<GenericResponseDTO> saveHobby(@Valid @RequestBody HobbyDTO hobbyDTO, @PathVariable String userId) {
        hobbyService.saveHobby(userId,hobbyDTO);
        return new ResponseEntity<>(new GenericResponseDTO(true, messageSource.getMessage("user.hobby.added.success",null, LocaleContextHolder.getLocale())), HttpStatus.CREATED);
    }
}
