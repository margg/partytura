package pl.edu.agh.tai.partytura.model;

import java.util.HashSet;
import java.util.Set;

public class Institution extends User {

  private EventFactory eventFactory;
  private Set<Event> createdEvents = new HashSet<>();
  private Set<String> genres = new HashSet<>();
  private PostFactory postFactory;

  public Institution(String username, EventFactory eventFactory, PostFactory postFactory) {
    super(username);
    this.eventFactory = eventFactory;
    this.postFactory = postFactory;
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
    this.postFactory.createPost(content, this);
  }
}
