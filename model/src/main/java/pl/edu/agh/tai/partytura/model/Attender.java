package pl.edu.agh.tai.partytura.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Attender extends User {

  private List<Institution> followedInstitutions = new ArrayList<>();

  public Attender(String username) {
    super(username);
  }

  public void follow(Institution institution) {
    if (!followedInstitutions.contains(institution)) {
      this.followedInstitutions.add(institution);
    }
  }

  public List<Institution> getFollowedInstitutions() {
    return Collections.unmodifiableList(followedInstitutions);
  }

  public void unfollow(Institution institution) throws UnfollowingNotFollowedInstitutionException {
    boolean removed = this.followedInstitutions.remove(institution);
    if (!removed) {
      throw new UnfollowingNotFollowedInstitutionException();
    }
  }
}
