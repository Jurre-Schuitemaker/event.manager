package com.snow.event.manager.event.mapper;

import com.snow.event.manager.event.dto.EventResponse;
import com.snow.event.manager.event.entity.Event;

public class EventMapper
{
    public static EventResponse toResponse(Event event)
    {
        return EventResponse.builder()
                .id(event.getId())
                .title(event.getTitle())
                .description(event.getDescription())
                .location(event.getLocation())
                .totalSeats(event.getTotalSeats())
                .availableSeats(event.getAvailableSeats())
                .price(event.getPrice())
                .date(event.getDate())
                .createdAt(event.getCreatedAt())
                .updatedAt(event.getUpdatedAt())
                .organizerId(event.getOrganizer().getId())
                .build();
    }
}
