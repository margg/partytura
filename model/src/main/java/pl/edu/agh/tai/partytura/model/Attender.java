package pl.edu.agh.tai.partytura.model;

import com.google.common.collect.ImmutableList;
import pl.edu.agh.tai.partytura.model.exceptions.UnfollowingNotFollowedInstitutionException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Attender extends User {

  private List<Institution> followedInstitutions = new ArrayList<>();
  private Set<Event> joinedEvents = new HashSet<>();
  private PostFactory postFactory;

  public Attender(String username, PostFactory postFactory) {
    super(username);
    this.postFactory = postFactory;
  }

  @Override
  public void addPost(String content, Event event) {
    event.addPost(postFactory.createPost(content, this));
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
