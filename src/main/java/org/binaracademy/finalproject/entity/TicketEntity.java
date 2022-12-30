package org.binaracademy.finalproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ticket")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TicketEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    private Long id;
    @Column(name = "status")
    private Boolean status;
    @Column(name = "schedule_id")
    private Long scheduleId;
    @Column(name = "seat_id")
    private Long seatId;
    @Column(name = "guest_id")
    private Long guestId;
    @Column(name = "order_id")
    private Long orderId;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "schedule_id", insertable = false, updatable = false)
    private ScheduleEntity schedule;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "seat_id", insertable = false, updatable = false)
    private SeatEntity seat;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "guest_id", insertable = false, updatable = false)
    private GuestEntity guest;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id", insertable = false, updatable = false, referencedColumnName = "order_id")
    private OrderEntity order;
    @Column(name = "create_at")
    private LocalDateTime createAt;
    @Column(name = "update_at")
    private LocalDateTime updateAt;

}
