package com.userprofile.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UserUpdateDTO {
    @Size(min = 2, max = 50, message = "{first.name.size}")
    @Pattern(regexp = "^[A-Za-z]+$", message = "{first.name.pattern}")
    private String firstName;

    @Size(max = 50, message = "{middle.name.size}")
    @Pattern(regexp = "^[A-Za-z]*$", message = "{middle.name.pattern}")
    private String middleName;

    @Size(min = 2, max = 50, message = "{last.name.size}")
    @Pattern(regexp = "^[A-Za-z]+$", message = "{last.name.pattern}")
    private String lastName;

    @Pattern(
            regexp = "^[6-9]\\d{9}$",
            message = "{company.contact.pattern}"
    )
    private String phoneNumber;
}
