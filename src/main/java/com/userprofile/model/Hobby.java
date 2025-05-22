package com.userprofile.model;

import com.userprofile.enums.HobbyType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Hobby {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String  id;

    private String hobbyName;

    private String description;

    @Enumerated(EnumType.STRING)
    private HobbyType hobbyType;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

}

