package com.snow.event.manager.booking.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.snow.event.manager.event.entity.Event;
import com.snow.event.manager.seat.entity.Seat;
import com.snow.event.manager.user.entity.User;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "booking")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @OneToMany
    @JoinColumn(name = "booking_id")
    private List<Seat> seats = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus status = BookingStatus.PENDING;

    @Column(nullable = false, updatable = false)
    private LocalDateTime bookingDate;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
 
    @NotNull
    private LocalDateTime updatedAt;

    /* ---- Lifecycle ---- */
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
 
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
