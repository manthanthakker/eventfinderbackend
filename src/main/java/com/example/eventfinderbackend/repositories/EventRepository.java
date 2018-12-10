package com.example.eventfinderbackend.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.eventfinderbackend.models.Event;

public interface EventRepository  extends CrudRepository<Event, String>{

}