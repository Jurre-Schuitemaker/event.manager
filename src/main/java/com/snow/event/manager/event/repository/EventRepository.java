package com.snow.event.manager.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.snow.event.manager.event.entity.Event;

public interface EventRepository extends JpaRepository<Event, Long> {}