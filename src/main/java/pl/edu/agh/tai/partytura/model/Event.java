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
  @JsonView(View.EventBase.class)
  private String id;

  @JsonView(View.EventBase.class)
  private String eventName;

  @JsonView(View.EventBase.class)
  private String hashtag;

  @JsonView(View.EventBase.class)
  private LocalDateTime dateTime;

  @JsonView(View.EventBase.class)
  private String location;

  @DBRef
  @JsonView(View.Event.class)
  private List<Post> posts = new ArrayList<>();

  public Event(){}

  @PersistenceConstructor
  public Event(String eventName, String hashtag, LocalDateTime dateTime, String location) {
    this(eventName, hashtag, dateTime, location, new ArrayList<>());
  }

  public Event(String eventName, String hashtag, LocalDateTime dateTime, String location, List<Post> posts) {
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

  public String getLocation() {
    return location;
  }

  public String getId() {
    return id;
  }

  public void setEventName(String eventName) {
    this.eventName = eventName;
  }

  public void setHashtag(String hashtag) {
    this.hashtag = hashtag;
  }

  public void setDateTime(LocalDateTime dateTime) {
    this.dateTime = dateTime;
  }

  public void setLocation(String location) {
    this.location = location;
  }
}
