package com.example.eventfinderbackend.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.eventfinderbackend.models.Event;
import com.example.eventfinderbackend.models.User;
import com.example.eventfinderbackend.repositories.EventRepository;
import com.example.eventfinderbackend.repositories.UserRepository;

@RestController
@CrossOrigin(origins = "*" , allowCredentials = "true" , allowedHeaders = "*")
public class EventService {
	@Autowired
	UserRepository userRepository;
	
	@Autowired
    EventRepository eventRepository;
	
	@PostMapping("/api/user/{userId}/event")
    public void createEvent(@RequestBody Event event, @PathVariable int userId) {
		Optional<User> op = userRepository.findById(userId);
		if(op != null) {
			User user = op.get();
			event.setOwner(user);
			eventRepository.save(event);
		}
    }
	
	@PutMapping("/api/user/{userId}/event/{eventId}")
	public void updateEvent(
			@PathVariable(name="userId") int userId,
			@PathVariable(name="courseId") String eventId,
			@RequestBody Event event) {
		Optional<User> op = userRepository.findById(userId);
		if(op != null) {
			User user = op.get();
			event.setOwner(user);
			eventRepository.save(event);
		}
	}
	
	@PostMapping("/api/user/{userId}/register")
    public void registerUser(@PathVariable int userId,
    						 @RequestBody Event event) {
		Optional<User> uOp = userRepository.findById(userId);
		if(uOp != null) {
			event.setRegisteredUsers(new ArrayList<User>());
			User user = uOp.get();
			user.getRegisteredEvents().add(event);
			if(!event.getRegisteredUsers().contains(user)) {
				event.getRegisteredUsers().add(user);
			}
			eventRepository.save(event);
		}
    }
	
	@GetMapping("/api/event")
	public List<Event> findAllEvents() {
		List<Event> eventList = (List<Event>) eventRepository.findAll();
		return eventList;
	}
	
	@GetMapping("/api/event/custom")
	public List<Event> findAllCustomEvents() {
		List<Event> eventList = (List<Event>) eventRepository.findAll();
		List<Event> customEvents = new ArrayList<Event>();
		for(Event e : eventList) {
			if(e.getOwner() != null) {
				customEvents.add(e);
			}
		}
		return customEvents;
	}
	
	 @GetMapping("/api/user/{userId}/event/registered")
	    public List<Event> findEventsForUser(@PathVariable int userId) {
	    	Optional<User> op = userRepository.findById(userId);
	    	if(op != null) {
	    		User user = op.get();
	    		return user.getRegisteredEvents();
	    	}
	    	return null;
	    }
	
	@GetMapping("/api/user/{userId}/event/hosted")
	public List<Event> findAllEventsForUser(@PathVariable int userId) {
		Optional<User> op = userRepository.findById(userId);
		if(op != null) {
			User user = op.get();
			return user.getHostedEvents();
		}
		return null;
	}
	
	@DeleteMapping("/api/event/{eventId}")
	public void deleteEvent(@PathVariable String eventId) {
		eventRepository.deleteById(eventId);
	}
	
	@GetMapping("/api/event/{eventId}")
	public Event findEventById(@PathVariable String eventId) {
		Optional<Event> op = eventRepository.findById(eventId);
		if(op != null) {
			return op.get();
		}
		return null;
	}
}
