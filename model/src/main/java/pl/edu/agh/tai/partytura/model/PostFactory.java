package pl.edu.agh.tai.partytura.model;

public class PostFactory {


  public Post createPost(String content) {
    return new Post(content);
  }
}
