package pl.edu.agh.tai.partytura.model;

import org.springframework.data.annotation.Id;
import pl.edu.agh.tai.partytura.model.factories.CommentFactory;
import pl.edu.agh.tai.partytura.model.factories.EventFactory;
import pl.edu.agh.tai.partytura.model.factories.PostFactory;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.Set;

public class Institution extends User {

  @Id
  private String id;

  @Inject
  private EventFactory eventFactory;

  @Inject
  private PostFactory postFactory;

  @Inject
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

  public void createEvent(String eventName) {
    Event event = eventFactory.createEvent(eventName);
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
    this.createdEvents = createdEvents;
  }

  public Set<String> getGenres(){
    return genres;
  }

  public void setGenres(Set<String> genres) {
    this.genres = genres;
  }
}
