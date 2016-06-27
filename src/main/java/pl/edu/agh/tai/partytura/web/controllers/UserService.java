package pl.edu.agh.tai.partytura.web.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.agh.tai.partytura.model.Attender;
import pl.edu.agh.tai.partytura.model.Institution;
import pl.edu.agh.tai.partytura.model.User;
import pl.edu.agh.tai.partytura.persistence.AttenderRepository;
import pl.edu.agh.tai.partytura.persistence.InstitutionRepository;

import java.util.List;
import java.util.Optional;

@Component
public class UserService {

  private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

  private AttenderRepository attenderRepository;

  private InstitutionRepository institutionRepository;

  @Autowired
  public UserService(AttenderRepository attenderRepository, InstitutionRepository institutionRepository) {
    this.attenderRepository = attenderRepository;
    this.institutionRepository = institutionRepository;
  }

  public Optional<User> getUser(String username) {
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

  public void save(User user) {
    if (user instanceof Attender) {
      attenderRepository.save((Attender) user);
    } else if (user instanceof Institution) {
      institutionRepository.save((Institution) user);
    } else {
      // TODO: log
    }
  }
}
