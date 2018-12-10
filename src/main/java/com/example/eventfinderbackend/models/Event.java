package com.example.eventfinderbackend.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Event {
	private static long idGenerator = 1;
	@Id
    private String id;
	private String name;
	private String description;
	private String category;
	private String location;
	private String startTime;
	private String endTime;
	private String date;
	
	@ManyToOne()
	@JsonIgnore
	private User owner;
	
	public Event() {
		this.id = "" + idGenerator++;
	}
	
	@Override
	public String toString() {
		return "Event [name=" + name + ", registeredUsers=" + registeredUsers + "]";
	}
	
	@ManyToMany(mappedBy="registeredEvents")
	List<User> registeredUsers;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public User getOwner() {
		return owner;
	}
	public List<User> getRegisteredUsers() {
		return registeredUsers;
	}
	public void setRegisteredUsers(List<User> registeredUsers) {
		this.registeredUsers = registeredUsers;
	}
	public void setOwner(User owner) {
		this.owner = owner;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}	
}
