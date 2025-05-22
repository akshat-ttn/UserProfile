package com.apnabaazar.userprofile.model;

import com.apnabaazar.userprofile.enums.HobbyType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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

