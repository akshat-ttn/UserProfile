package com.userprofile.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    @JsonProperty(access =  JsonProperty.Access.READ_ONLY)
    private String id;

    @NotBlank(message = "{first.name.required}")
    @Size(min = 2, max = 50, message = "{first.name.size}")
    @Pattern(regexp = "^[A-Za-z]+$", message = "{first.name.pattern}")
    private String firstName;


    @Size(max = 50, message = "{middle.name.size}")
    @Pattern(regexp = "^[A-Za-z]*$", message = "{middle.name.pattern}")
    private String middleName;

    @NotBlank(message = "{last.name.required}")
    @Size(min = 2, max = 50, message = "{last.name.size}")
    @Pattern(regexp = "^[A-Za-z]+$", message = "{last.name.pattern}")
    private String lastName;

    @NotBlank(message = "{email.required}")
    @Email(message = "{email.invalid}")
    @Size(max = 255, message = "{email.size}")
    @Pattern(
            regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$",
            message = "{email.invalid}"
    )
    private String email;

    @NotBlank(message = "{contact.required}")
    @Pattern(
            regexp = "^[6-9]\\d{9}$",
            message = "{contact.pattern}"
    )
    private String phoneNumber;

    @NotBlank(message = "{password.required}")
    @Size(min = 8, max = 15, message = "{password.size}")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]).{8,15}$",
            message = "{password.pattern}"
    )
    private String password;

    @NotBlank(message = "{confirm.password.required}")
    private String confirmPassword;

    @JsonProperty(access =  JsonProperty.Access.READ_ONLY)
    private boolean active;





}


