package pl.edu.agh.tai.partytura.model;

import java.util.HashSet;
import java.util.Set;

public class Institution extends User {

  private EventFactory eventFactory;
  private Set<Event> createdEvents = new HashSet<>();
  private Set<String> genres = new HashSet<>();

  public Institution(String username, EventFactory eventFactory) {
    super(username);
    this.eventFactory = eventFactory;
  }

  public void createEvent(String eventName) {
    Event event = eventFactory.createEvent(eventName);
    createdEvents.add(event);
  }

  public Set<Event> getCreatedEvents() {
    return createdEvents;
  }

  public void addGenres(String genre){
    genres.add(genre);
  }

  public Set<String> getGenres(){
    return genres;
  }

  @Override
  public void addPost(String content, Event event) {

  }
}
