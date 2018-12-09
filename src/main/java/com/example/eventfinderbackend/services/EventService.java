package com.example.eventfinderbackend.services;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    public void createUser(@RequestBody Event event, @PathVariable int userId) {
		Optional<User> op = userRepository.findById(userId);
		if(op != null) {
			User user = op.get();
			event.setOwner(user);
			eventRepository.save(event);
		}
    }
	
	@PostMapping("/api/user/{userId}/event/{eventId}")
    public void registerUser(@PathVariable int userId,
    					   @PathVariable int eventId) {
		Optional<User> uOp = userRepository.findById(userId);
		Optional<Event> eOp = eventRepository.findById(eventId);
		if(uOp != null && eOp != null) {
			User user = uOp.get();
			Event event = eOp.get();
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
	public void deleteEvent(@PathVariable int eventId) {
		eventRepository.deleteById(eventId);
	}
}