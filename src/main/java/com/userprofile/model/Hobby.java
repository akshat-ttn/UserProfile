package com.userprofile.model;

import com.userprofile.enums.HobbyType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Hobby {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String  id;

    private String hobbyName;

    private String description;

    @Enumerated(EnumType.STRING)
    private HobbyType hobbyType;

}

