package org.springframework.social.showcase.model.factories;

import org.springframework.social.showcase.model.Comment;
import org.springframework.social.showcase.model.User;
import org.springframework.stereotype.Component;

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
