package com.userprofile.service;

import com.userprofile.dto.UserDTO;
import com.userprofile.dto.UserUpdateDTO;
import com.userprofile.exceptions.EmailAlreadyInUseException;
import com.userprofile.exceptions.PasswordMismatchException;
import com.userprofile.exceptions.UserNotFoundException;
import com.userprofile.model.User;
import com.userprofile.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final MessageSource messageSource;

     public void saveUser(UserDTO userDTO) {
        if (!userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
            log.warn("Password mismatch for email: {}", userDTO.getEmail());
            throw new PasswordMismatchException(messageSource.getMessage("password.mismatch", null, LocaleContextHolder.getLocale()));
        }
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            log.warn("Email already in use: {}", userDTO.getEmail());
            throw new EmailAlreadyInUseException(messageSource.getMessage("email.in.use", new Object[]{userDTO.getEmail()}, LocaleContextHolder.getLocale()));
        }

        User user = User.builder()
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .phoneNumber(userDTO.getPhoneNumber())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .build();
        if (userDTO.getMiddleName() != null && !userDTO.getMiddleName().isEmpty()) {
            user.setMiddleName(userDTO.getMiddleName());
        }
        userRepository.save(user);
    }

    public List<UserDTO> getAllUsers(int pageSize, int pageOffset, String sort, String direction, String email) {
        Sort.Direction sortDirection = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable  pageable = PageRequest.of(pageOffset, pageSize, Sort.by(sortDirection,sort));
        Page<User> userPage;
        if (email != null && !email.isBlank())
            userPage = userRepository.findByEmailContainingIgnoreCase(email, pageable);
        else
            userPage = userRepository.findAll(pageable);

        return userPage.stream()
                .map(user -> UserDTO.builder()
                        .id(user.getId())
                        .firstName(user.getFirstName())
                        .middleName(user.getMiddleName())
                        .lastName(user.getLastName())
                        .phoneNumber(user.getPhoneNumber())
                        .email(user.getEmail())
                        .active(user.isActive())
                        .build())
                .toList();
    }


    public void updateUser(String id, UserUpdateDTO userDTO) {
        User user = userRepository.findById(id).
                orElseThrow(() -> new UserNotFoundException(messageSource.getMessage("user.not.found", new Object[]{id},LocaleContextHolder.getLocale())));

        user.setFirstName(getUpdatedValue(userDTO.getFirstName(),user.getFirstName()));
        user.setMiddleName(userDTO.getMiddleName());
        user.setLastName(getUpdatedValue(userDTO.getLastName(),user.getLastName()));
        user.setPhoneNumber(getUpdatedValue(userDTO.getPhoneNumber(),user.getPhoneNumber()));

        userRepository.save(user);
    }


    private String getUpdatedValue(String newValue, String oldValue) {
        return (newValue != null && !newValue.isBlank()) ? newValue : oldValue;
    }

    public void deleteUser(String id) {
        User user = userRepository.findById(id).
                orElseThrow(() -> new UserNotFoundException(messageSource.getMessage("user.not.found", new Object[]{id},LocaleContextHolder.getLocale())));

        userRepository.delete(user);
    }
}
