package pl.edu.agh.tai.partytura.model.factories;

import org.springframework.stereotype.Component;
import pl.edu.agh.tai.partytura.model.Event;

import java.time.LocalDateTime;

@Component
public class EventFactory {

  public Event createEvent(String eventName, String hashtag, LocalDateTime dateTime, String location) {
    return new Event(eventName, hashtag, dateTime, location);
  }
}
