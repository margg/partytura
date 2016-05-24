package pl.edu.agh.tai.partytura.model;

import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.time.LocalDateTime;

public class Comment {

  @DBRef
  private final User author;

  private final String content;
  private final LocalDateTime dateTime;

  @PersistenceConstructor
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
