package com.snow.event.manager.seat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.snow.event.manager.seat.entity.Seat;
import com.snow.event.manager.seat.entity.SeatStatus;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Long>
{
    List<Seat> findByEventId(Long eventId);
    List<Seat> findByEventIdAndStatus(Long eventId, SeatStatus status);
}
