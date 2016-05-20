package pl.edu.agh.tai.partytura.model;

import com.google.common.collect.ImmutableList;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Post {

  private String content;
  private User author;
  private LocalDateTime dateTime;
  private List<Comment> comments = new ArrayList<>();

  public Post(String content, User author, LocalDateTime dateTime) {
    this.content = content;
    this.author = author;
    this.dateTime = dateTime;
  }

  public void addComment(Comment comment) {
    this.comments.add(comment);
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

  public List<Comment> getComments() {
    return ImmutableList.copyOf(comments);
  }

  public void setComments(List<Comment> comments) {
    this.comments = new ArrayList<>(comments);
  }
}
