package com.snow.event.manager.event.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.snow.event.manager.user.entity.User;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "event")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Event
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotBlank
    private String location;

    @NotNull
    private LocalDateTime date;

    @Min(1)
    private int totalSeats;

    @Min(0)
    private int availableSeats;

    @PositiveOrZero
    @NotBlank
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizer_id", nullable = false)
    private User organizer;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
 
    @NotNull
    private LocalDateTime updatedAt;
 
    /* ---- Lifecycle ---- */
    @PrePersist
    protected void onCreate() 
    {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
 
    @PreUpdate
    protected void onUpdate() 
    {
        this.updatedAt = LocalDateTime.now();
    }
}
