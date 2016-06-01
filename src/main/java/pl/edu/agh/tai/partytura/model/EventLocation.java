package pl.edu.agh.tai.partytura.model;

import com.fasterxml.jackson.annotation.JsonView;
import pl.edu.agh.tai.partytura.web.View;

public class EventLocation {

  // TODO: make it smarter :)
  @JsonView(View.Attender.class)
  private String location;

  public EventLocation(String location) {
    this.location = location;
  }

  public String getLocation() {
    return location;
  }
}
