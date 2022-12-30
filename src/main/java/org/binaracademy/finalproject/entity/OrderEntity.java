package org.binaracademy.finalproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "orders")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class OrderEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;
    @Column(name = "status")
    private Boolean status;
    @Column(name = "total_price")
    private BigDecimal totalPrice;
    @Column(name = "tax")
    private BigDecimal tax;
    @Column(name = "total_pay")
    private BigDecimal totalPay;
    @Column(name = "expired_at")
    private LocalDateTime expiredAt;
    @Column(name = "email_user")
    private String userEmail;
    @Column(name = "schedule_id")
    private Long scheduleId;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "schedule_id", insertable = false, updatable = false)
    private ScheduleEntity schedule;
    @Column(name = "create_at")
    private LocalDateTime createAt;
    @Column(name = "update_at")
    private LocalDateTime updateAt;
    @OneToMany(orphanRemoval = true, mappedBy = "order", cascade = CascadeType.ALL)
    private List<TicketEntity> ticket = new ArrayList<>();

}
