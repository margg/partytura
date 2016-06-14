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

    List<Attender> attenders = attenderRepository.findByTwitterId(userProfile.getId());
    List<Institution> institutions = institutionRepository.findByTwitterId(userProfile.getId());

    model.addAttribute("twitterProfile", userProfile);

    User user;
    if (isNewUser(attenders, institutions)) {
      return "/role";
    } else if (!attenders.isEmpty()) {
      // attender
      if (attenders.size() > 1) {
        LOGGER.warn("Found {} users with ID = {}.", attenders.size(), userProfile.getId());
      }
      user = attenders.get(0);
      model.addAttribute("user", user);
      return "/attenderDashboard";
    } else if (!institutions.isEmpty()) {
      // institution
      if (institutions.size() > 1) {
        LOGGER.warn("Found {} users with ID = {}.", institutions.size(), userProfile.getId());
      }
      user = institutions.get(0);
      model.addAttribute("user", user);
      return "/instDashboard";
    }
    return "/error";
  }

  private boolean isNewUser(List<? extends User> attenders, List<? extends User> institutions) {
    return attenders.isEmpty() && institutions.isEmpty();
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
        Attender attender = attenderRepository.insert(new Attender(name, id));
        model.addAttribute("user", attender);
        return "redirect:/dashboard";
      case "Institution":
        Institution institution = institutionRepository.insert(new Institution(name, id));
        model.addAttribute("user", institution);
        return "redirect:/dashboard";
      default:
        return "/error";
    }
  }

}