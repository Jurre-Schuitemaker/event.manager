package com.snow.event.manager.seat.mapper;

import com.snow.event.manager.seat.dto.SeatResponse;
import com.snow.event.manager.seat.entity.Seat;
 
public class SeatMapper
{
    public static SeatResponse toResponse(Seat seat)
    {
        return SeatResponse.builder()
            .id(seat.getId())
            .seatNumber(seat.getSeatNumber())
            .status(seat.getStatus())
            .eventId(seat.getEvent().getId())
            .build();
    }
}