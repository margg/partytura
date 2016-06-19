package org.springframework.social.showcase.model;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.social.showcase.model.exceptions.UnfollowingNotFollowedInstitutionException;

import java.util.HashSet;
import java.util.Set;

@Document
public class Attender extends User {

  @Id
  private String id;

  @DBRef
  private Set<Institution> followedInstitutions;

  @DBRef
  private Set<Event> joinedEvents;

  @PersistenceConstructor
  public Attender(String username) {
    this(username, new HashSet<>(), new HashSet<>());
  }

  public Attender(String username, Set<Institution> followedInstitutions, Set<Event> joinedEvents) {
    super(username);
    this.followedInstitutions = followedInstitutions;
    this.joinedEvents = joinedEvents;
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

  @Required
  public void setFollowedInstitutions(Set<Institution> followedInstitutions) {
    this.followedInstitutions = new HashSet<>(followedInstitutions);
  }

  public Set<Event> getJoinedEvents() {
    return joinedEvents;
  }

  @Required
  public void setJoinedEvents(Set<Event> joinedEvents) {
    this.joinedEvents = new HashSet<>(joinedEvents);
  }
}
