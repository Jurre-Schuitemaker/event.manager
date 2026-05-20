package com.snow.event.manager.seat.controller;

import java.util.List;
 
import org.springframework.web.bind.annotation.*;
 
import com.snow.event.manager.seat.dto.SeatResponse;
import com.snow.event.manager.seat.service.SeatService;
 
import lombok.RequiredArgsConstructor;
 
@RestController
@RequestMapping("/events/{eventId}/seats")
@RequiredArgsConstructor
public class SeatController
{
    private final SeatService seatService;
 
    @GetMapping
    public List<SeatResponse> getAllSeats(@PathVariable Long eventId)
    {
        return seatService.getSeatsByEvent(eventId);
    }
 
    @GetMapping("/available")
    public List<SeatResponse> getAvailableSeats(@PathVariable Long eventId)
    {
        return seatService.getAvailableSeatsByEvent(eventId);
    }
 
    @GetMapping("/{seatId}")
    public SeatResponse getSeat(@PathVariable Long eventId, @PathVariable Long seatId)
    {
        return seatService.getSeat(seatId);
    }
}