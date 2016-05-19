package pl.edu.agh.tai.partytura.model;

public abstract class User {
  private String username;

  public User(String username) {
    this.username = username;
  }

  public String getUsername() {
    return username;
  }

  public abstract void addPost(String content, Event event);
}
