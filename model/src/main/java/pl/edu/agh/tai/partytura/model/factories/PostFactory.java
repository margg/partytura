package pl.edu.agh.tai.partytura.model.factories;

import pl.edu.agh.tai.partytura.model.Post;
import pl.edu.agh.tai.partytura.model.User;

import java.sql.Timestamp;
import java.util.Date;

public class PostFactory {

  public Post createPost(String content, User author) {
    return createPost(content, author, new Timestamp(new Date().getTime()));
  }

  public Post createPost(String content, User author, Timestamp timestamp) {
    return new Post(content, author, timestamp);
  }
}
