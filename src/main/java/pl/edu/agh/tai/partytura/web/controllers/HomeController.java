package pl.edu.agh.tai.partytura.web.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import pl.edu.agh.tai.partytura.model.User;
import pl.edu.agh.tai.partytura.persistence.AttenderRepository;
import pl.edu.agh.tai.partytura.persistence.EventRepository;
import pl.edu.agh.tai.partytura.persistence.InstitutionRepository;
import pl.edu.agh.tai.partytura.web.View;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {

  private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

  private Twitter twitter;

  private ConnectionRepository connectionRepository;

  @Autowired
  private AttenderRepository attenderRepository;

  @Autowired
  private EventRepository eventRepository;

  @Autowired
  private InstitutionRepository institutionRepository;

  @Autowired
  public HomeController(Twitter twitter, ConnectionRepository connectionRepository) {
    this.twitter = twitter;
    this.connectionRepository = connectionRepository;
  }

  @RequestMapping(method = RequestMethod.GET)
  public String homePage(Model model) {
    return "index";
  }

  @RequestMapping(path = "/dashboard", method = RequestMethod.GET)
  public String userHomePage(Model model) {
    if (connectionRepository.findPrimaryConnection(Twitter.class) == null) {
      return "redirect:/connect/twitter";
    }

    TwitterProfile userProfile = twitter.userOperations().getUserProfile();
    model.addAttribute("twitterProfile", userProfile);

    User user = getUser(userProfile.getId());
    if (user == null) {
      return "/role";
    }

    model.addAttribute("user", user);

    if (user instanceof Attender) {
      return "/attenderDashboard";
    } else if (user instanceof Institution) {
      return "/instDashboard";
    }
    return "/error";
  }

  private User getUser(long id) {
/*    List<Attender> attenders = attenderRepository.findByTwitterId(id);
    List<Institution> institutions = institutionRepository.findByTwitterId(id);
    if (!attenders.isEmpty()) {
      // attender
      if (attenders.size() > 1) {
        LOGGER.warn("Found {} users with ID = {}.", attenders.size(), id);
      }
      return attenders.get(0);
    } else if (!institutions.isEmpty()) {
      // institution
      if (institutions.size() > 1) {
        LOGGER.warn("Found {} users with ID = {}.", institutions.size(), id);
      }
      return institutions.get(0);
    }*/
    return null;
  }

  @RequestMapping(path = "/dashboard", method = RequestMethod.POST)
  public String chooseRole(HttpServletRequest request, Model model) {
    if (connectionRepository.findPrimaryConnection(Twitter.class) == null) {
      return "redirect:/connect/twitter";
    }
    TwitterProfile userProfile = twitter.userOperations().getUserProfile();

    long id = userProfile.getId();
    String name = userProfile.getName();

    switch (request.getParameter("role")) {
      case "Attender":
        Attender attender = attenderRepository.insert(new Attender(name));
        model.addAttribute("user", attender);
        return "redirect:/dashboard";
      case "Institution":
        Institution institution = institutionRepository.insert(new Institution(name));
        model.addAttribute("user", institution);
        return "redirect:/dashboard";
      default:
        return "/error";
    }
  }

}