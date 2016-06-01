package pl.edu.agh.tai.partytura.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.edu.agh.tai.partytura.web.View;

import java.time.LocalDateTime;

@Document
public class Comment {

  @Id
  private String id;

  @DBRef
  @JsonBackReference
  private User author;

  @JsonView(View.Event.class)
  private String content;

  @JsonView(View.Event.class)
  private LocalDateTime dateTime;

  public Comment() {}

  @PersistenceConstructor
  public Comment(String content, User author, LocalDateTime dateTime) {
    this.content = content;
    this.author = author;
    this.dateTime = dateTime;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public User getAuthor() {
    return author;
  }

  public void setAuthor(User author) {
    this.author = author;
  }

  public LocalDateTime getDateTime() {
    return dateTime;
  }

  public void setDateTime(LocalDateTime dateTime) {
    this.dateTime = dateTime;
  }
}
