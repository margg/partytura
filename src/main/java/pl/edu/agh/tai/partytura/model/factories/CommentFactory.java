package pl.edu.agh.tai.partytura.model.factories;

import org.springframework.stereotype.Component;
import pl.edu.agh.tai.partytura.model.Comment;
import pl.edu.agh.tai.partytura.model.User;

import java.time.LocalDateTime;

@Component
public class CommentFactory {

  public Comment createComment(String content, User author) {
    return createComment(content, author, LocalDateTime.now());
  }

  public Comment createComment(String content, User author, LocalDateTime dateTime) {
    return new Comment(content, author, dateTime);
  }
}
