package pl.edu.agh.tai.partytura;

import com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.edu.agh.tai.partytura.model.*;
import pl.edu.agh.tai.partytura.model.factories.CommentFactory;
import pl.edu.agh.tai.partytura.model.factories.EventFactory;
import pl.edu.agh.tai.partytura.model.factories.PostFactory;
import pl.edu.agh.tai.partytura.persistence.AttenderRepository;
import pl.edu.agh.tai.partytura.persistence.EventRepository;
import pl.edu.agh.tai.partytura.persistence.InstitutionRepository;

import java.time.LocalDateTime;

@SpringBootApplication
public class PartyturaWebappApplication implements CommandLineRunner {

  @Autowired
  private AttenderRepository attenderRepository;

  @Autowired
  private InstitutionRepository institutionRepository;

  @Autowired
  private EventRepository eventRepository;

  public static void main(String[] args) {
    SpringApplication.run(PartyturaWebappApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {

    Attender john = createAttender("a1", "john", new PostFactory(), new CommentFactory());
    Attender stanley = createAttender("a2", "stanley", new PostFactory(), new CommentFactory());
    Attender keira = createAttender("a3", "keira", new PostFactory(), new CommentFactory());
    Attender aisha = createAttender("a4", "aisha", new PostFactory(), new CommentFactory());

    attenderRepository.save(ImmutableList.of(john, stanley, keira, aisha));

    Institution iceKrakow = createInstitution("i1", "icekrakow", new EventFactory(), new PostFactory(), new CommentFactory());
    Institution ckrotunda = createInstitution("i2", "ckrotunda", new EventFactory(), new PostFactory(), new CommentFactory());
    Institution bagatela = createInstitution("i3", "teatrbagatela", new EventFactory(), new PostFactory(), new CommentFactory());

    institutionRepository.save(ImmutableList.of(iceKrakow, ckrotunda, bagatela));

    Event elvislives = createEvent("e1", "Elvis lives!", "elvislives", LocalDateTime.of(2016, 7, 10, 18, 0), new EventLocation("ICE Kraków"));
    Event hanszimmer = createEvent("e2", "Hans Zimmer live", "hanszimmerlive", LocalDateTime.of(2016, 7, 15, 18, 0), new EventLocation("ICE Kraków"));
    Event luckychops = createEvent("e3", "Lucky Chops Cracow", "luckychopscracow", LocalDateTime.of(2016, 7, 20, 18, 0), new EventLocation("CK Rotunda"));
    Event mayday = createEvent("e4", "Mayday", "maydaybagatela", LocalDateTime.of(2016, 7, 22, 18, 0), new EventLocation("Teatr Bagatela"));

    eventRepository.save(ImmutableList.of(elvislives, hanszimmer, luckychops));

    iceKrakow.addEvent(elvislives);
    iceKrakow.addEvent(hanszimmer);
    ckrotunda.addEvent(luckychops);
    bagatela.addEvent(mayday);

    // join events
    john.joinEvent(elvislives);
    john.joinEvent(hanszimmer);

    stanley.joinEvent(elvislives);
    stanley.joinEvent(hanszimmer);
    stanley.joinEvent(luckychops);

    keira.joinEvent(elvislives);
    keira.joinEvent(mayday);

    aisha.joinEvent(hanszimmer);
    aisha.joinEvent(luckychops);

    // create some posts and comments
    keira.addPost("Elvis is the King!", elvislives);
    Post keirasPost = elvislives.getPosts().get(0);

    stanley.addComment("Yup!", keirasPost);

    eventRepository.save(ImmutableList.of(elvislives, hanszimmer, luckychops));
    attenderRepository.save(ImmutableList.of(john, stanley, keira, aisha));
    institutionRepository.save(ImmutableList.of(iceKrakow, ckrotunda, bagatela));

  }

  private Event createEvent(String id, String eventName, String hashtag, LocalDateTime dateTime, EventLocation location) {
    Event event = new Event(eventName, hashtag, dateTime, location);
    event.setId(id);
    return event;
  }

  private Institution createInstitution(String id, String name, EventFactory eventFactory, PostFactory postFactory,
                                        CommentFactory commentFactory) {
    Institution institution = new Institution(name, eventFactory, postFactory, commentFactory);
    institution.setId(id);
    return institution;
  }

  private Attender createAttender(String id, String username, PostFactory postFactory, CommentFactory commentFactory) {
    Attender attender = new Attender(username, postFactory, commentFactory);
    attender.setId(id);
    return attender;
  }

}
