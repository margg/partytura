package pl.edu.agh.tai.partytura.web.signup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.*;
import org.springframework.social.connect.web.ProviderSignInUtils;
import pl.edu.agh.tai.partytura.web.config.UserType;
import pl.edu.agh.tai.partytura.model.Attender;
import pl.edu.agh.tai.partytura.model.Institution;
import pl.edu.agh.tai.partytura.model.User;
import pl.edu.agh.tai.partytura.persistence.AttenderRepository;
import pl.edu.agh.tai.partytura.persistence.InstitutionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import pl.edu.agh.tai.partytura.web.signin.SignInUtils;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class SignupController {
  private final ProviderSignInUtils signInUtils;

  @Autowired
  private AttenderRepository attenders;

  @Autowired
  private InstitutionRepository institutions;

  @Autowired
  public SignupController(ConnectionFactoryLocator connectionFactoryLocator, UsersConnectionRepository connectionRepository) {
    this.signInUtils = new ProviderSignInUtils(connectionFactoryLocator, connectionRepository);
  }

  @RequestMapping(value = "/signup", method = RequestMethod.GET)
  public String signupForm(WebRequest request, Model model) {
    Connection<?> connectionFromSession = signInUtils.getConnectionFromSession(request);
    return Optional.<Connection<?>>ofNullable(connectionFromSession).map(connection -> {
      SignupForm signupForm = SignupForm.fromProviderUser(connection.fetchUserProfile());
      model.addAttribute("form", signupForm);
      model.addAttribute("types", UserType.values());
      return "/signup";
    }).orElse("redirect:/signin");
  }

  @RequestMapping(value = "/signup", method = RequestMethod.POST)
  public String signup(@Valid SignupForm form, BindingResult formBinding, WebRequest request) {
    if (formBinding.hasErrors()) {
      return "/error";
    }

    Optional<User> created = createAccount(form);
    created.ifPresent(user -> {
      SignInUtils.signin(user.getUsername());
      signInUtils.doPostSignUp(user.getUsername(), request);
    });

    return created.isPresent() ? "redirect:/" : "/error";
  }

  private Optional<User> createAccount(SignupForm form) {
    switch (form.getType()) {
      case ATTENDER:
        Attender attender = attenders.insert(new Attender(form.getUsername()));
        return Optional.of(attender);
      case INSTITUTION:
        Institution institution = institutions.insert(new Institution(form.getUsername()));
        return Optional.of(institution);
      default:
        return Optional.empty();
    }
  }
}
