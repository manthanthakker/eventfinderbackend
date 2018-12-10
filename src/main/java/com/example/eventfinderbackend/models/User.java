package com.example.eventfinderbackend.models;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class User {

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userType='" + userType + '\'' +
                ", aboutMe='" + aboutMe + '\'' +
                ", hostedEvents=" + hostedEvents +
                ", registeredEvents=" + registeredEvents +
                '}';
    }

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String userType;
    private String aboutMe;


    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    @OneToMany(mappedBy="owner")
    private List<Event> hostedEvents;
    
    @JsonIgnore
    @ManyToMany
    @JoinTable(name="USER_EVENT", joinColumns=@JoinColumn(name="USER_ID", referencedColumnName="ID"), 
    							  inverseJoinColumns=@JoinColumn(name="EVENT_ID", referencedColumnName="ID"))
    private List<Event> registeredEvents;


    public User() {}
    public User(String username) {
        this.username = username;
    }
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public User(String aboutMe, String userType, String firstName, String lastName, String username, String password) {
        this(username, password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.userType = userType;
        this.aboutMe = aboutMe;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public List<Event> getRegisteredEvents() {
		return registeredEvents;
	}
	public void setRegisteredEvents(List<Event> registeredEvents) {
		this.registeredEvents = registeredEvents;
	}
	public List<Event> getHostedEvents() {
		return hostedEvents;
	}
	public void setHostedEvents(List<Event> hostedEvents) {
		this.hostedEvents = hostedEvents;
	}
	public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public void hostEvent(Event event) {
    	this.hostedEvents.add(event);
    }
}
