package pl.edu.agh.tai.partytura.model;

public class Post {

  private String content;
  private User author;

  public Post(String content, User author) {
    this.content = content;
    this.author = author;
  }
}
