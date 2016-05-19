package pl.edu.agh.tai.partytura.model;

import com.google.common.collect.ImmutableList;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Post {

  private String content;
  private User author;
  private Timestamp timestamp;
  private List<Comment> comments = new ArrayList<>();

  public Post(String content, User author, Timestamp timestamp) {
    this.content = content;
    this.author = author;
    this.timestamp = timestamp;
  }

  public void addComment(Comment comment) {
    this.comments.add(comment);
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

  public List<Comment> getComments() {
    return ImmutableList.copyOf(comments);
  }
}
