package pl.edu.agh.tai.partytura.model;

public class PostFactory {

  public Post createPost(String content, User author) {
    return new Post(content, author);
  }
}
