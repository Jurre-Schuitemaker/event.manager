package com.snow.event.manager.event.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.snow.event.manager.event.dto.CreateEventRequest;
import com.snow.event.manager.event.dto.EventResponse;
import com.snow.event.manager.event.dto.UpdateEventRequest;
import com.snow.event.manager.event.service.EventService;
import com.snow.event.manager.user.entity.User;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController
{
    private final EventService eventService;

    @PostMapping
    public EventResponse createEvent(
        @Valid @RequestBody CreateEventRequest request,
        @AuthenticationPrincipal User organizer
    ) {
        return eventService.createEvent(request, organizer);
    }

    @GetMapping("/{id}")
    public EventResponse getEvent(@PathVariable Long id)
    {
        return eventService.getEvent(id);
    }

    @PutMapping("/{id}")
    public EventResponse updateEvent(
        @PathVariable Long id,

        @Valid
        @RequestBody UpdateEventRequest request
    ) {
        return eventService.updateEvent(id, request);
    }
}
