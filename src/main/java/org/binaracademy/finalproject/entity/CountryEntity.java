package org.binaracademy.finalproject.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "country")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CountryEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "country_id")
    private Long id;
    @Column(name = "country_name", nullable = false)
    private String name;
    @Column(name = "create_at")
    @JsonFormat(pattern = "YYYY/MM/dd HH:mm:ss")
    private LocalDateTime createAt;
    @JsonFormat(pattern = "YYYY/MM/dd HH:mm:ss")
    @Column(name = "update_at")
    private LocalDateTime updateAt;

}
