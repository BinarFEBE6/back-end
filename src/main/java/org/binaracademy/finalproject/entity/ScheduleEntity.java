package org.binaracademy.finalproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "schedules")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ScheduleEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Long id;
    @Column(name = "departure_airport", nullable = false)
    private String departureAiport;
    @Column(name = "arrival_airport", nullable = false)
    private String arrivalAirport;
    @Column(name = "price", nullable = false)
    private BigDecimal price;
    @Column(name = "max_seat", nullable = false)
    private Integer maxSeat;
    @Column(name = "date_departure", nullable = false)
    private LocalDate date;
    @Column(name = "schedule_time_id")
    private Long scheduleTimeId;
    @Column(name = "category_class_id")
    private Long categoryClassId;
    @Column(name = "pesawat_id")
    private Long pesawatId;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "schedule_time_id", insertable = false, updatable = false)
    private ScheduleTimeEntity scheduleTime;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "category_class_id", insertable = false, updatable = false)
    private CategoryClassEntity categoryClass;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "pesawat_id", insertable = false, updatable = false)
    private PesawatEntity pesawat;
    @Column(name = "create_at")
    private LocalDateTime createAt;
    @Column(name = "update_at")
    private LocalDateTime updateAt;

}
