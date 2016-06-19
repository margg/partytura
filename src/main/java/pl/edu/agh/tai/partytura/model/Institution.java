package pl.edu.agh.tai.partytura.model;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Document
public class Institution extends User {

  @Id
  private String id;

  private Set<String> genres;

  @DBRef
  private Set<Event> createdEvents;

  @PersistenceConstructor
  public Institution(String username) {
    this(username, new HashSet<>(), new HashSet<>());
  }

  public Institution(String username, Set<String> genres, Set<Event> createdEvents) {
    super(username);
    this.genres = genres;
    this.createdEvents = createdEvents;
  }

  public void addEvent(Event event) {
    createdEvents.add(event);
  }

  public void addGenre(String genre) {
    genres.add(genre);
  }

  public Set<Event> getCreatedEvents() {
    return createdEvents;
  }

  @Required
  public void setCreatedEvents(Set<Event> createdEvents) {
    this.createdEvents = new HashSet<>(createdEvents);
  }

  public Set<String> getGenres() {
    return genres;
  }

  @Required
  public void setGenres(Set<String> genres) {
    this.genres = new HashSet<>(genres);
  }
}
