package pl.edu.agh.tai.partytura.model;

import com.google.common.collect.ImmutableSet;
import org.springframework.data.annotation.Id;
import pl.edu.agh.tai.partytura.model.exceptions.UnfollowingNotFollowedInstitutionException;
import pl.edu.agh.tai.partytura.model.factories.CommentFactory;
import pl.edu.agh.tai.partytura.model.factories.PostFactory;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.Set;

public class Attender extends User {

  @Id
  private String id;

  @Inject
  private PostFactory postFactory;

  @Inject
  private CommentFactory commentFactory;

  private Set<Institution> followedInstitutions = new HashSet<>();
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
    return ImmutableSet.copyOf(followedInstitutions);
  }

  public void setFollowedInstitutions(Set<Institution> followedInstitutions) {
    this.followedInstitutions = followedInstitutions;
  }

  public Set<Event> getJoinedEvents() {
    return ImmutableSet.copyOf(joinedEvents);
  }

  public void setJoinedEvents(Set<Event> joinedEvents) {
    this.joinedEvents = new HashSet<>(joinedEvents);
  }
}
