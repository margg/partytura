package pl.edu.agh.tai.partytura.model;

import com.fasterxml.jackson.annotation.JsonView;
import pl.edu.agh.tai.partytura.web.View;

public abstract class User {

  @JsonView(View.User.class)
  private String username;

  private long twitterId;

  public User(String username, long twitterId) {
    this.username = username;
    this.twitterId = twitterId;
  }

  public String getUsername() {
    return username;
  }

  public long getTwitterId() {
    return twitterId;
  }
}
