package pl.edu.agh.tai.partytura.model;

import java.sql.Timestamp;

public class Comment {

  private final String content;
  private final User author;
  private final Timestamp timestamp;

  public Comment(String content, User author, Timestamp timestamp) {
    this.content = content;
    this.author = author;
    this.timestamp = timestamp;
  }

  public String getContent() {
    return content;
  }

  public User getAuthor() {
    return author;
  }

  public Timestamp getTimestamp() {
    return timestamp;
  }
}
