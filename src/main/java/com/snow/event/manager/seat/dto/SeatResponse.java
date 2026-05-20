package com.snow.event.manager.seat.dto;

import com.snow.event.manager.seat.entity.SeatStatus;
import lombok.Builder;
import lombok.Getter;
 
@Getter
@Builder
public class SeatResponse
{
    private Long id;
    private int seatNumber;
    private SeatStatus status;
    private Long eventId;
}
