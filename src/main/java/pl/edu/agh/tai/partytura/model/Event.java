package pl.edu.agh.tai.partytura.model;

import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Event {

  private String eventName;
  private String hashtag;
  private Date date;
  private EventLocation location;
  private List<Post> posts = new ArrayList<>();

  public Event(String eventName, String hashtag, Date date, EventLocation location) {
    this.eventName = eventName;
    this.hashtag = hashtag;
    this.date = date;
    this.location = location;
  }

  public void addPost(Post post) {
    this.posts.add(post);
  }

  public List<Post> getPosts() {
    return ImmutableList.copyOf(posts);
  }

  public String getEventName() {
    return eventName;
  }
}
