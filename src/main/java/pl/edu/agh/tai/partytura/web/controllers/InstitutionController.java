package pl.edu.agh.tai.partytura.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.edu.agh.tai.partytura.model.*;
import pl.edu.agh.tai.partytura.model.exceptions.UnfollowingNotFollowedInstitutionException;
import pl.edu.agh.tai.partytura.persistence.InstitutionRepository;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class InstitutionController {

  private InstitutionRepository institutionRepository;

  private UserService userService;

  @Autowired
  public InstitutionController(InstitutionRepository institutionRepository, UserService userService) {
    this.institutionRepository = institutionRepository;
    this.userService = userService;
  }

  @RequestMapping(path = "/institutions", method = RequestMethod.GET)
  public String showEvents(Principal currentUser, Model model) {
    Optional<User> user = userService.getUser(currentUser.getName());
    return user.map(u -> {
      List<Institution> institutions = institutionRepository.findAll();
      model.addAttribute("institutions", institutions);
      return "/institutions";
    }).orElse("redirect:/signin");
  }

  @RequestMapping(path = "/institution/{instName}", method = RequestMethod.GET)
  public String eventHomePage(Principal currentUser, @PathVariable("instName") String instName, Model model) {
    Optional<User> user = userService.getUser(currentUser.getName());
    return user.map(u -> {
      return userService.getUser(instName).map(inst -> {
        if (inst instanceof Institution) {
          model.addAttribute("institution", inst);
          addFollowButtonInfoIfNeeded(model, u, (Institution) inst);
          return "/institution";
        }
        return "redirect:/error";
      }).orElse("redirect:/error");
    }).orElse("redirect:/signin");
  }

  private void addFollowButtonInfoIfNeeded(Model model, User u, Institution inst) {
    if (u instanceof Attender) {
      boolean isFollowing = ((Attender) u).getFollowedInstitutions().contains(inst);
      model.addAttribute("showFollowButton", !isFollowing);
      model.addAttribute("followButtonText", "Follow");
      model.addAttribute("unfollowButtonText", "Unfollow");
    }
  }

  @RequestMapping(path = "/institution/{instName}/follow", method = RequestMethod.POST)
  public String follow(Principal currentUser, @PathVariable("instName") String instName, Model model) {
    Optional<User> user = userService.getUser(currentUser.getName());
    return user.map(u -> {
      return userService.getUser(instName).map(institution -> {
        if (u instanceof Attender && institution instanceof Institution) {
          ((Attender) u).follow((Institution) institution);
          userService.save(u);
          model.addAttribute("institution", institution);
          return "redirect:/institution/" + instName;
        }
        return "redirect:/error";
      }).orElse("redirect:/error");
    }).orElse("redirect:/signin");
  }

  @RequestMapping(path = "/institution/{instName}/unfollow", method = RequestMethod.POST)
  public String unfollow(Principal currentUser, @PathVariable("instName") String instName, Model model) {
    Optional<User> user = userService.getUser(currentUser.getName());
    return user.map(u -> {
      return userService.getUser(instName).map(institution -> {
        try {
          if (u instanceof Attender && institution instanceof Institution) {
            ((Attender) u).unfollow((Institution) institution);
            userService.save(u);
            model.addAttribute("institution", institution);
            return "redirect:/institution/" + instName;
          }
        } catch (UnfollowingNotFollowedInstitutionException e) {
          // TODO: log
        }
        return "redirect:/error";
      }).orElse("redirect:/error");
    }).orElse("redirect:/signin");
  }
}
