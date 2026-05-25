package com.snow.event.manager.event.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.snow.event.manager.event.entity.SeatingType;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateEventRequest
{
    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotBlank
    private String location;

    private LocalDateTime date;

    @Min(1)
    private int totalSeats;

    @PositiveOrZero
    private BigDecimal price;

    @NotNull
    private SeatingType seatingType;
}
