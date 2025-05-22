package com.userprofile.service;

import com.userprofile.dto.HobbyDTO;
import com.userprofile.exceptions.UserNotFoundException;
import com.userprofile.model.Hobby;
import com.userprofile.model.User;
import com.userprofile.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class HobbyService {
    private final UserRepository userRepository;
    private final MessageSource messageSource;

    public void saveHobby(String userId, HobbyDTO hobbyDTO) {
        User user = userRepository.findById(userId).
                orElseThrow(() -> new UserNotFoundException(messageSource.getMessage("user.not.found", new Object[]{userId}, LocaleContextHolder.getLocale())));

        Hobby hobby = Hobby.builder()
                .hobbyName(hobbyDTO.getHobbyName())
                .description(hobbyDTO.getDescription())
                .hobbyType(hobbyDTO.getHobbyType())
                .build();

        user.getHobbies().add(hobby);
        userRepository.save(user);
    }

    public List<HobbyDTO> getAllHobbies(String userId) {
        User user = userRepository.findById(userId).
                orElseThrow(() -> new UserNotFoundException(messageSource.getMessage("user.not.found", new Object[]{userId}, LocaleContextHolder.getLocale())));

        return user.getHobbies().stream()
                .map(hobby -> HobbyDTO.builder()
                        .id(hobby.getId())
                        .hobbyName(hobby.getHobbyName())
                        .description(hobby.getDescription())
                        .hobbyType(hobby.getHobbyType())
                        .build())
                .toList();
    }
}
