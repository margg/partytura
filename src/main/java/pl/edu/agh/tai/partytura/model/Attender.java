package pl.edu.agh.tai.partytura.model;

import com.google.common.collect.ImmutableList;
import pl.edu.agh.tai.partytura.model.factories.CommentFactory;
import pl.edu.agh.tai.partytura.model.exceptions.UnfollowingNotFollowedInstitutionException;
import pl.edu.agh.tai.partytura.model.factories.PostFactory;

import java.util.*;

public class Attender extends User {

  private String id;

  private List<Institution> followedInstitutions = new ArrayList<>();
  private Set<Event> joinedEvents = new HashSet<>();
  private PostFactory postFactory;
  private CommentFactory commentFactory;

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
    if (!followedInstitutions.contains(institution)) {
      this.followedInstitutions.add(institution);
    }
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

  public List<Institution> getFollowedInstitutions() {
    return Collections.unmodifiableList(followedInstitutions);
  }

  public List<Event> getJoinedEvents() {
    return ImmutableList.copyOf(joinedEvents);
  }
}
