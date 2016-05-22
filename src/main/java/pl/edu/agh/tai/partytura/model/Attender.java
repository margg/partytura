package pl.edu.agh.tai.partytura.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.stereotype.Component;
import pl.edu.agh.tai.partytura.model.exceptions.UnfollowingNotFollowedInstitutionException;
import pl.edu.agh.tai.partytura.model.factories.CommentFactory;
import pl.edu.agh.tai.partytura.model.factories.PostFactory;

import java.util.HashSet;
import java.util.Set;

@Component
public class Attender extends User {

  @Id
  private String id;

  @Autowired
  @Transient
  private PostFactory postFactory;

  @Autowired
  @Transient
  private CommentFactory commentFactory;

  @DBRef
  private Set<Institution> followedInstitutions = new HashSet<>();

  @DBRef
  private Set<Event> joinedEvents = new HashSet<>();

  public Attender() {}

  public Attender(String username) {
    super(username);
  }

  public Attender(String username, PostFactory postFactory, CommentFactory commentFactory) {
    super(username);
    this.postFactory = postFactory;
    this.commentFactory = commentFactory;
  }

  @Override
  public void addPost(String content, Event event) {
    event.addPost(postFactory.createPost(content, this));
  }

  @Override
  public void addComment(String content, Post post) {
    post.addComment(commentFactory.createComment(content, this));
  }

  public void follow(Institution institution) {
    this.followedInstitutions.add(institution);
  }

  public void unfollow(Institution institution) throws UnfollowingNotFollowedInstitutionException {
    boolean removed = this.followedInstitutions.remove(institution);
    if (!removed) {
      throw new UnfollowingNotFollowedInstitutionException();
    }
  }

  public void joinEvent(Event event) {
    this.joinedEvents.add(event);
  }


  public Set<Institution> getFollowedInstitutions() {
    return followedInstitutions;
  }

  public void setFollowedInstitutions(Set<Institution> followedInstitutions) {
    this.followedInstitutions = new HashSet<>(followedInstitutions);
  }

  public Set<Event> getJoinedEvents() {
    return joinedEvents;
  }

  public void setJoinedEvents(Set<Event> joinedEvents) {
    this.joinedEvents = new HashSet<>(joinedEvents);
  }

  public PostFactory getPostFactory() {
    return postFactory;
  }

  public void setPostFactory(PostFactory postFactory) {
    this.postFactory = postFactory;
  }

  public CommentFactory getCommentFactory() {
    return commentFactory;
  }

  public void setCommentFactory(CommentFactory commentFactory) {
    this.commentFactory = commentFactory;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
}
