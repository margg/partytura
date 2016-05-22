package pl.edu.agh.tai.partytura.model;

import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class Event {

  @Id
  private String id;

  private String eventName;
  private String hashtag;
  private LocalDateTime dateTime;
  private EventLocation location;
  private List<Post> posts = new ArrayList<>();

  public Event() {}

  public Event(String eventName, String hashtag, LocalDateTime dateTime, EventLocation location) {
    this.eventName = eventName;
    this.hashtag = hashtag;
    this.dateTime = dateTime;
    this.location = location;
  }

  public void addPost(Post post) {
    this.posts.add(post);
  }

  public List<Post> getPosts() {
    return posts;
  }

  public void setPosts(List<Post> posts) {
    this.posts = new ArrayList<>(posts);
  }

  public String getEventName() {
    return eventName;
  }

  public void setEventName(String eventName) {
    this.eventName = eventName;
  }

  public String getHashtag() {
    return hashtag;
  }

  public void setHashtag(String hashtag) {
    this.hashtag = hashtag;
  }

  public LocalDateTime getDateTime() {
    return dateTime;
  }

  public void setDateTime(LocalDateTime dateTime) {
    this.dateTime = dateTime;
  }

  public EventLocation getLocation() {
    return location;
  }

  public void setLocation(EventLocation location) {
    this.location = location;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
}
