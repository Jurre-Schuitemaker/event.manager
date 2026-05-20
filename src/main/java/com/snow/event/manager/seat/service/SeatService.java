package com.snow.event.manager.seat.service;

import java.util.List;
 
import org.springframework.stereotype.Service;
 
import com.snow.event.manager.seat.dto.SeatResponse;
import com.snow.event.manager.seat.entity.Seat;
import com.snow.event.manager.seat.entity.SeatStatus;
import com.snow.event.manager.seat.mapper.SeatMapper;
import com.snow.event.manager.seat.repository.SeatRepository;
 
import lombok.RequiredArgsConstructor;
 
@Service
@RequiredArgsConstructor
public class SeatService
{
    private final SeatRepository seatRepository;
 
    public List<SeatResponse> getSeatsByEvent(Long eventId)
    {
        return seatRepository.findByEventId(eventId).stream()
            .map(SeatMapper::toResponse)
            .toList();
    }
 
    public List<SeatResponse> getAvailableSeatsByEvent(Long eventId)
    {
        return seatRepository.findByEventIdAndStatus(eventId, SeatStatus.AVAILABLE).stream()
            .map(SeatMapper::toResponse)
            .toList();
    }
 
    public SeatResponse getSeat(Long seatId)
    {
        Seat seat = seatRepository.findById(seatId)
            .orElseThrow(() -> new RuntimeException("Seat not found"));
        return SeatMapper.toResponse(seat);
    }
}
