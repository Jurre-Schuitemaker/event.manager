package com.snow.event.manager.event.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.snow.event.manager.event.dto.CreateEventRequest;
import com.snow.event.manager.event.dto.EventResponse;
import com.snow.event.manager.event.dto.UpdateEventRequest;
import com.snow.event.manager.event.entity.Event;
import com.snow.event.manager.event.entity.SeatingType;
import com.snow.event.manager.event.mapper.EventMapper;
import com.snow.event.manager.event.repository.EventRepository;
import com.snow.event.manager.seat.entity.Seat;
import com.snow.event.manager.seat.entity.SeatStatus;
import com.snow.event.manager.seat.repository.SeatRepository;
import com.snow.event.manager.user.entity.User;

import jakarta.transaction.Transactional;
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
            .seatingType(request.getSeatingType())
            .organizer(organizer)
            .build();
 
        Event savedEvent = eventRepository.save(event);
 
       if (savedEvent.getSeatingType() == SeatingType.ASSIGNED) {
            List<Seat> seats = new ArrayList<>();
            for (int i = 1; i <= savedEvent.getTotalSeats(); i++) {
                seats.add(Seat.builder()
                    .event(savedEvent)
                    .seatNumber(i)
                    .status(SeatStatus.AVAILABLE)
                    .build());
            }
            seatRepository.saveAll(seats);
        }
 
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

        int oldTotal = event.getTotalSeats();
        int newTotal = request.getTotalSeats();

        if (event.getSeatingType() == SeatingType.ASSIGNED)
        {
            if (newTotal > oldTotal)
            {
                List<Seat> newSeats = new ArrayList<>();
                for (int i = oldTotal + 1; i <= newTotal; i++)
                {
                    newSeats.add(Seat.builder()
                        .event(event)
                        .seatNumber(i)
                        .status(SeatStatus.AVAILABLE)
                        .build());
                }
                seatRepository.saveAll(newSeats);
            }
            else if (newTotal < oldTotal)
            {
                List<Seat> seatsToRemove = seatRepository.findByEventIdAndSeatNumberGreaterThan(id, newTotal);
        
                boolean hasBookedSeats = seatsToRemove.stream()
                    .anyMatch(s -> s.getStatus() == SeatStatus.BOOKED 
                                || s.getStatus() == SeatStatus.RESERVED);
        
                if (hasBookedSeats)
                {
                    throw new RuntimeException(
                        "Cannot reduce seats: seats above " + newTotal + " are booked or reserved");
                }
        
                seatRepository.deleteAll(seatsToRemove);
            }

            long bookedCount = seatRepository.findByEventId(id).stream()
                .filter(s -> s.getStatus() != SeatStatus.AVAILABLE)
                .count();

            event.setAvailableSeats(newTotal - (int) bookedCount);
        }
        else
        {
             int diff = newTotal - oldTotal;
            event.setAvailableSeats(event.getAvailableSeats() + diff);
        }

        event.setTitle(request.getTitle());
        event.setDescription(request.getDescription());
        event.setLocation(request.getLocation());
        event.setDate(request.getDate());
        event.setTotalSeats(newTotal);
        event.setPrice(request.getPrice());

        Event updatedEvent = eventRepository.save(event);
        return EventMapper.toResponse(updatedEvent);
    }

    @Transactional
    public void deleteEvent(Long id, User organizer)
    {
        Event event = eventRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Event not found"));
    
        if (!event.getOrganizer().getId().equals(organizer.getId()) && !organizer.isAdmin()) {
            throw new RuntimeException("You are not the organizer of this event");
        }
    
        seatRepository.deleteByEventId(event.getId());
        eventRepository.delete(event);
    }
}