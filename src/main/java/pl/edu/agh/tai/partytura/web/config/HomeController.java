package pl.edu.agh.tai.partytura.web.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.edu.agh.tai.partytura.model.Attender;
import pl.edu.agh.tai.partytura.model.Institution;
import pl.edu.agh.tai.partytura.model.User;
import pl.edu.agh.tai.partytura.persistence.AttenderRepository;
import pl.edu.agh.tai.partytura.persistence.InstitutionRepository;

import javax.inject.Provider;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {
  private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

  private final Provider<ConnectionRepository> connectionRepositoryProvider;

  @Autowired
  private AttenderRepository attenderRepository;

  @Autowired
  private InstitutionRepository institutionRepository;

  @Autowired
  public HomeController(Provider<ConnectionRepository> connectionRepositoryProvider) {
    this.connectionRepositoryProvider = connectionRepositoryProvider;
  }

  @RequestMapping("/")
  public String home(Principal currentUser, Model model) {
    ConnectionRepository connectionRepository = connectionRepositoryProvider.get();
    Connection<Twitter> connection = connectionRepository.findPrimaryConnection(Twitter.class);
    UserProfile profile = connection.fetchUserProfile();
    model.addAttribute(profile);

    String username = profile.getUsername();
    Optional<User> user = getUser(username);
    return user.map(u -> {
      model.addAttribute("user", u);
      return u instanceof Attender ? "attenderDashboard" : "instDashboard";
    }).orElse("/error");
  }

  private Optional<User> getUser(String username) {
    Optional<User> user = Optional.empty();
    List<Attender> attenders = attenderRepository.findByUsername(username);
    List<Institution> institutions = institutionRepository.findByUsername(username);
    if (!attenders.isEmpty()) {
      if (attenders.size() > 1) {
        LOGGER.warn("Found {} users with ID = {}.", attenders.size(), username);
      }
      user = Optional.of(attenders.get(0));
    } else if (!institutions.isEmpty()) {
      if (institutions.size() > 1) {
        LOGGER.warn("Found {} users with ID = {}.", institutions.size(), username);
      }
      user = Optional.of(institutions.get(0));
    }
    return user;
  }
}
