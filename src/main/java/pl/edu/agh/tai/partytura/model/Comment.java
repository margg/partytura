package pl.edu.agh.tai.partytura.model;

import java.time.LocalDateTime;

public class Comment {

  private final String content;
  private final User author;
  private final LocalDateTime dateTime;

  public Comment(String content, User author, LocalDateTime dateTime) {
    this.content = content;
    this.author = author;
    this.dateTime = dateTime;
  }

  public String getContent() {
    return content;
  }

  public User getAuthor() {
    return author;
  }

  public LocalDateTime getDateTime() {
    return dateTime;
  }
}
