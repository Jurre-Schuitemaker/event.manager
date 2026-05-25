package com.snow.event.manager.event.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.snow.event.manager.user.dto.UserResponse;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventResponse
{
    private long id;

    private String title;

    private String description;

    private String location;

    private int totalSeats;

    private int availableSeats;

    private BigDecimal price;

    private LocalDateTime date;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Long organizerId;
}
