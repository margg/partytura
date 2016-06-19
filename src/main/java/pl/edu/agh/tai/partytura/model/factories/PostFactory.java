package pl.edu.agh.tai.partytura.model.factories;

import pl.edu.agh.tai.partytura.model.Post;
import pl.edu.agh.tai.partytura.model.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PostFactory {

  public Post createPost(String content, User author) {
    return createPost(content, author, LocalDateTime.now());
  }

  public Post createPost(String content, User author, LocalDateTime dateTime) {
    return new Post(content, author, dateTime);
  }
}
