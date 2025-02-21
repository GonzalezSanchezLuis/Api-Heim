package com.holi.api.users.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.holi.api.ScheduleMove.domain.entity.ReservationMoving;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String fullName;
    private String email;
    private String password;
    private String  document;
    private String  phone;
    private String urlAvatarProfile;
    private String role;
    private LocalDateTime createdAt;
    private boolean isActive;

    @PrePersist
    public void prePersist(){
        createdAt = LocalDateTime.now();
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<ReservationMoving> reservationMovingList = new ArrayList<>();

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                // Omitir la lista de 'reservationMovingList'
                '}';
    }

}
