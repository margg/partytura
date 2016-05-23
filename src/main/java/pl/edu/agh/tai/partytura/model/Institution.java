package pl.edu.agh.tai.partytura.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.stereotype.Component;
import pl.edu.agh.tai.partytura.model.factories.CommentFactory;
import pl.edu.agh.tai.partytura.model.factories.EventFactory;
import pl.edu.agh.tai.partytura.model.factories.PostFactory;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Component
public class Institution extends User {

  @Id
  private String id;

  @Autowired
  @Transient
  private EventFactory eventFactory;

  @Autowired
  @Transient
  private PostFactory postFactory;

  @Autowired
  private CommentFactory commentFactory;

  private Set<String> genres;

  @DBRef
  private Set<Event> createdEvents;

  public Institution() {}

  public Institution(String username, long twitterId, EventFactory eventFactory, PostFactory postFactory, CommentFactory commentFactory) {
    this(username, twitterId, new HashSet<>(), new HashSet<>());
    this.eventFactory = eventFactory;
    this.postFactory = postFactory;
    this.commentFactory = commentFactory;
  }

  public Institution(String username, long twitterId, Set<String> genres, Set<Event> createdEvents) {
    super(username, twitterId);
    this.genres = new HashSet<>(genres);
    this.createdEvents = new HashSet<>(createdEvents);
  }

  public void createEvent(String eventName, String hashtag, LocalDateTime dateTime, EventLocation location) {
    Event event = eventFactory.createEvent(eventName, hashtag, dateTime, location);
    addEvent(event);
  }

  public void addEvent(Event event) {
    createdEvents.add(event);
  }

  public void addGenre(String genre){
    genres.add(genre);
  }

  @Override
  public void addPost(String content, Event event) {
    event.addPost(this.postFactory.createPost(content, this));
  }

  @Override
  public void addComment(String content, Post post) {
    post.addComment(this.commentFactory.createComment(content, this));
  }

  public Set<Event> getCreatedEvents() {
    return createdEvents;
  }

  public void setCreatedEvents(Set<Event> createdEvents) {
    this.createdEvents = new HashSet<>(createdEvents);
  }

  public Set<String> getGenres(){
    return genres;
  }

  public void setGenres(Set<String> genres) {
    this.genres = new HashSet<>(genres);
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
}
