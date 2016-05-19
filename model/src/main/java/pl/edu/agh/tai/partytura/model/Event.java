package pl.edu.agh.tai.partytura.model;

import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;

public class Event {

  private String eventName;
  private List<Post> posts = new ArrayList<>();

  public Event(String eventName) {
    this.eventName = eventName;
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
