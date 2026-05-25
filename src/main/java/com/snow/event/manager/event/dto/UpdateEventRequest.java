package com.snow.event.manager.event.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateEventRequest
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
}
