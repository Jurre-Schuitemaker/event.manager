package com.snow.event.manager.event.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.snow.event.manager.event.dto.CreateEventRequest;
import com.snow.event.manager.event.dto.EventResponse;
import com.snow.event.manager.event.dto.UpdateEventRequest;
import com.snow.event.manager.event.entity.Event;
import com.snow.event.manager.event.mapper.EventMapper;
import com.snow.event.manager.event.repository.EventRepository;
import com.snow.event.manager.seat.entity.Seat;
import com.snow.event.manager.seat.entity.SeatStatus;
import com.snow.event.manager.seat.repository.SeatRepository;
import com.snow.event.manager.user.entity.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final SeatRepository seatRepository;

    public EventResponse createEvent(CreateEventRequest request, User organizer)
    {
        Event event = Event.builder()
            .title(request.getTitle())
            .description(request.getDescription())
            .location(request.getLocation())
            .date(request.getDate())
            .totalSeats(request.getTotalSeats())
            .availableSeats(request.getTotalSeats())
            .price(request.getPrice())
            .organizer(organizer)
            .build();
 
        Event savedEvent = eventRepository.save(event);
 
        List<Seat> seats = new ArrayList<>();
        for (int i = 1; i <= savedEvent.getTotalSeats(); i++)
        {
            seats.add(Seat.builder()
                .event(savedEvent)
                .seatNumber(i)
                .status(SeatStatus.AVAILABLE)
                .build());
        }
        seatRepository.saveAll(seats);
 
        return EventMapper.toResponse(savedEvent);
    }

    public EventResponse getEvent(Long id)
    {
        Event event = eventRepository.findById(id)
            .orElseThrow(() ->
                new RuntimeException("Event not found"));

        return EventMapper.toResponse(event);
    }

    public EventResponse updateEvent(Long id, UpdateEventRequest request)
    {
        Event event = eventRepository.findById(id)
            .orElseThrow(() ->
                new RuntimeException("Event not found"));

        event.setTitle(request.getTitle());
        event.setDescription(request.getDescription());
        event.setLocation(request.getLocation());
        event.setDate(request.getDate());
        event.setTotalSeats(request.getTotalSeats());
        event.setPrice(request.getPrice());

        Event updatedEvent = eventRepository.save(event);

        return EventMapper.toResponse(updatedEvent);
    }
}