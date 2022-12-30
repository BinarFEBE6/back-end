package org.binaracademy.finalproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "schedule_time")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ScheduleTimeEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_time_id")
    private Long id;
    @Column(name = "day", nullable = false)
    private String day;
    @Column(name = "depature_time", nullable = false)
    private LocalTime depatureTime;
    @Column(name = "arrival_time", nullable = false)
    private LocalTime arrivalTime;
    @Column(name = "create_at")
    private LocalDateTime createAt;
    @Column(name = "update_at")
    private LocalDateTime updateAt;

}
