package pl.edu.agh.tai.partytura.web.signup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import pl.edu.agh.tai.partytura.persistence.AttenderRepository;
import pl.edu.agh.tai.partytura.persistence.InstitutionRepository;
import org.springframework.stereotype.Component;

@Component
public class RepositoriesAwareConnectionSignup implements ConnectionSignUp {
  private static final Logger logger = LoggerFactory.getLogger(RepositoriesAwareConnectionSignup.class);
  private final AttenderRepository attenders;
  private final InstitutionRepository institutions;

  @Autowired
  public RepositoriesAwareConnectionSignup(AttenderRepository attenders, InstitutionRepository institutions) {
    this.attenders = attenders;
    this.institutions = institutions;
  }

  @Override
  public String execute(Connection<?> connection) {
    String username = connection.fetchUserProfile().getUsername();
    return userExists(username) ? username : null;
  }

  private boolean userExists(String username) {
    boolean isAttender = !attenders.findByUsername(username).isEmpty();
    boolean isInstitution = !institutions.findByUsername(username).isEmpty();
    logger.info("Connection resolved as: username={}, isAttender={}, isInstitution={}", username, isAttender, isInstitution);
    return isAttender || isInstitution;
  }
}
