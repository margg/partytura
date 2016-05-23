package pl.edu.agh.tai.partytura.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.edu.agh.tai.partytura.model.Attender;
import pl.edu.agh.tai.partytura.model.Event;
import pl.edu.agh.tai.partytura.model.Institution;
import pl.edu.agh.tai.partytura.persistence.AttenderRepository;
import pl.edu.agh.tai.partytura.persistence.EventRepository;
import pl.edu.agh.tai.partytura.persistence.InstitutionRepository;

import javax.inject.Inject;
import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {

  private Twitter twitter;

  private ConnectionRepository connectionRepository;

  @Autowired
  private AttenderRepository attenderRepository;

  @Autowired
  private EventRepository eventRepository;

  @Autowired
  private InstitutionRepository institutionRepository;

  @Inject
  public HomeController(Twitter twitter, ConnectionRepository connectionRepository) {
    this.twitter = twitter;
    this.connectionRepository = connectionRepository;
  }

  @RequestMapping(method = RequestMethod.GET)
  public String homePage(Model model) {
    return "main";
  }

  @RequestMapping(path = "dashboard", method = RequestMethod.GET)
  public String userHomePage(Model model) {
    if (connectionRepository.findPrimaryConnection(Twitter.class) == null) {
      return "redirect:/connect/twitter";
    }

    TwitterProfile userProfile = twitter.userOperations().getUserProfile();

    List<Attender> attenders = attenderRepository.findByTwitterId(userProfile.getId());

    Attender attender;
    if (attenders.isEmpty()) {
      // new user! yay!
      attender = attenderRepository.insert(new Attender(userProfile.getName(), userProfile.getId()));

      Event elvislives = eventRepository.findAll().get(0);
      attender.joinEvent(elvislives);
      List<Institution> institutions = institutionRepository.findAll();
      for (Institution institution : institutions) {
        attender.follow(institution);
      }

      attenderRepository.save(attender);

    } else {
      if (attenders.size() > 1) {
        // TODO: log
        System.err.println("Strange... Taking the first attender.");
      }
      attender = attenders.get(0);
    }

    model.addAttribute("twitterProfile", userProfile);
    model.addAttribute("attender", attender);
    return "dashboard";
  }

}