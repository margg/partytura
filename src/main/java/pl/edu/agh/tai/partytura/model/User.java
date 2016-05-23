package pl.edu.agh.tai.partytura.model;

public abstract class User {

  private String username;

  private long twitterId;

  public User() {}

  public User(String username, long twitterId) {
    this.username = username;
    this.twitterId = twitterId;
  }

  public abstract void addPost(String content, Event event);

  public abstract void addComment(String content, Post post);

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public long getTwitterId() {
    return twitterId;
  }

  public void setTwitterId(long twitterId) {
    this.twitterId = twitterId;
  }
}
