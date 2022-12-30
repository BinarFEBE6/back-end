package org.binaracademy.finalproject.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "guests")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class GuestEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "guest_id")
    private Long id;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;
    @Column(name = "nationality", nullable = false)
    private String nationality;
    @Column(name = "country", nullable = false)
    private String country;
    @Column(name = "passport")
    private String passport;
    @Column(name = "end_passport")
    private LocalDate endPassport;
    @Column(name = "google_id")
    private String googleId;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "contact_id")
    private Long contactId;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private UserEntity user;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "contact_id", insertable = false, updatable = false)
    private ContactGuestEntity contact;
    @Column(name = "create_at")
    private LocalDateTime createAt;
    @Column(name = "update_at")
    private LocalDateTime updateAt;


}
