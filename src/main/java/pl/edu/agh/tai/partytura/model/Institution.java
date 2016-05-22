package pl.edu.agh.tai.partytura.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
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
  private EventFactory eventFactory;

  @Autowired
  private PostFactory postFactory;

  @Autowired
  private CommentFactory commentFactory;

  private Set<Event> createdEvents = new HashSet<>();
  private Set<String> genres = new HashSet<>();

  public Institution() {}

  public Institution(String username) {
    super(username);
  }

  public Institution(String username, EventFactory eventFactory, PostFactory postFactory, CommentFactory commentFactory) {
    super(username);
    this.eventFactory = eventFactory;
    this.postFactory = postFactory;
    this.commentFactory = commentFactory;
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
