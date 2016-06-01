package pl.edu.agh.tai.partytura.web.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.tai.partytura.model.*;
import pl.edu.agh.tai.partytura.persistence.*;
import pl.edu.agh.tai.partytura.web.View;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class AttenderController {

  private Twitter twitter;

  private ConnectionRepository connectionRepository;

  @Autowired
  private AttenderRepository attenderRepository;

  @Autowired
  private EventRepository eventRepository;

  @Autowired
  private InstitutionRepository institutionRepository;

  @Autowired
  private PostRepository postRepository;

  @Autowired
  private CommentRepository commentRepository;

  @Autowired
  public AttenderController(Twitter twitter, ConnectionRepository connectionRepository) {
    this.twitter = twitter;
    this.connectionRepository = connectionRepository;
  }

  @JsonView(View.Attender.class)
  @RequestMapping(path = "/api/attender/{attenderId}", method = RequestMethod.GET, produces = "application/json")
  public @ResponseBody Attender getAttender(@PathVariable("attenderId") String attenderId, Model model) {
    // TODO: check permissions
/*    if (connectionRepository.findPrimaryConnection(Twitter.class) == null) {
      return "redirect:/connect/twitter";
    }*/

    return attenderRepository.findOne(attenderId);
  }

  private User getUser(long id, String username) {
    // TODO: totally rewrite :D
    List<Attender> attenders = attenderRepository.findByUsername(username);
    List<Institution> institutions = institutionRepository.findByUsername(username);
    User user;
    if (!attenders.isEmpty()) {
      user = attenders.get(0);
    } else if (!institutions.isEmpty()) {
      user = institutions.get(0);
    } else {
      // new user! yay!
      Attender newAttender = attenderRepository.insert(new Attender(username, id));
      attenderRepository.save(newAttender);
      user = newAttender;
    }
    return user;
  }

}
