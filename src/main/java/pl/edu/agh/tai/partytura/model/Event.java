package pl.edu.agh.tai.partytura.model;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.edu.agh.tai.partytura.web.View;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document
public class Event {

  @Id
  private String id;

  @JsonView(View.Attender.class)
  private String eventName;

  @JsonView(View.Attender.class)
  private String hashtag;

  @JsonView(View.Attender.class)
  private LocalDateTime dateTime;

  @JsonView(View.Attender.class)
  private EventLocation location;

  @DBRef
  private List<Post> posts;

  @PersistenceConstructor
  public Event(String eventName, String hashtag, LocalDateTime dateTime, EventLocation location) {
    this(eventName, hashtag, dateTime, location, new ArrayList<>());
  }

  public Event(String eventName, String hashtag, LocalDateTime dateTime, EventLocation location, List<Post> posts) {
    this.eventName = eventName;
    this.hashtag = hashtag;
    this.dateTime = dateTime;
    this.location = location;
    this.posts = posts;
  }

  public void addPost(Post post) {
    this.posts.add(post);
  }

  public List<Post> getPosts() {
    return posts;
  }

  @Required
  public void setPosts(List<Post> posts) {
    this.posts = new ArrayList<>(posts);
  }

  public String getEventName() {
    return eventName;
  }

  public String getHashtag() {
    return hashtag;
  }

  public LocalDateTime getDateTime() {
    return dateTime;
  }

  public EventLocation getLocation() {
    return location;
  }

  public String getId() {
    return id;
  }
}
