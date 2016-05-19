package pl.edu.agh.tai.partytura.model.factories;

import pl.edu.agh.tai.partytura.model.Comment;
import pl.edu.agh.tai.partytura.model.User;

import java.sql.Timestamp;
import java.util.Date;

public class CommentFactory {
  public Comment createComment(String content, User author) {
    return createComment(content, author, new Timestamp(new Date().getTime()));
  }

  public Comment createComment(String content, User author, Timestamp timestamp) {
    return new Comment(content, author, timestamp);
  }
}
