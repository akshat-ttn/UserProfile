package com.userprofile.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.userprofile.enums.HobbyType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class HobbyDTO {

    @JsonProperty(access =  JsonProperty.Access.READ_ONLY)
    private String id;

    @NotBlank(message = "{hobby.name.required}")
    @Size(min = 2, max = 50, message = "{hobby.name.size}")
    private String hobbyName;

    @Size(max = 255, message = "{hobby.description.size}")
    private String description;

    @NotNull(message = "{hobby.type.required}")
    private HobbyType hobbyType;
}
