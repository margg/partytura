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

    attenderRepository.deleteAll();
    institutionRepository.deleteAll();
    eventRepository.deleteAll();

    Attender john = attenderRepository.insert(createAttender("john", 1, new PostFactory(), new CommentFactory()));
    Attender stanley = attenderRepository.insert(createAttender("stanley", 2, new PostFactory(), new CommentFactory()));
    Attender keira = attenderRepository.insert(createAttender("keira", 3, new PostFactory(), new CommentFactory()));
    Attender aisha = attenderRepository.insert(createAttender("aisha", 4, new PostFactory(), new CommentFactory()));

    Institution iceKrakow = institutionRepository.insert(createInstitution("icekrakow", 5, new EventFactory(), new PostFactory(), new CommentFactory()));
    Institution ckrotunda = institutionRepository.insert(createInstitution("ckrotunda", 6, new EventFactory(), new PostFactory(), new CommentFactory()));
    Institution bagatela = institutionRepository.insert(createInstitution("teatrbagatela", 7, new EventFactory(), new PostFactory(), new CommentFactory()));

    Event elvislives = eventRepository.insert(createEvent("Elvis lives!", "elvislives",
            LocalDateTime.of(2016, 7, 10, 18, 0), new EventLocation("ICE Kraków")));
    Event hanszimmer = eventRepository.insert(createEvent("Hans Zimmer live", "hanszimmerlive",
            LocalDateTime.of(2016, 7, 15, 18, 0), new EventLocation("ICE Kraków")));
    Event luckychops = eventRepository.insert(createEvent("Lucky Chops Cracow", "luckychopscracow",
            LocalDateTime.of(2016, 7, 20, 18, 0), new EventLocation("CK Rotunda")));
    Event mayday = eventRepository.insert(createEvent("Mayday", "maydaybagatela",
            LocalDateTime.of(2016, 7, 22, 18, 0), new EventLocation("Teatr Bagatela")));

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

  private Event createEvent(String eventName, String hashtag, LocalDateTime dateTime, EventLocation location) {
    return new Event(eventName, hashtag, dateTime, location);
  }

  private Institution createInstitution(String name, long twitterId, EventFactory eventFactory,
                                        PostFactory postFactory, CommentFactory commentFactory) {
    return new Institution(name, twitterId, eventFactory, postFactory, commentFactory);
  }

  private Attender createAttender(String username, long twitterId, PostFactory postFactory,
                                  CommentFactory commentFactory) {
    return new Attender(username, twitterId, postFactory, commentFactory);
  }

}
