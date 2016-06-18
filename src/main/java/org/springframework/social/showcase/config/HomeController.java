package org.springframework.social.showcase.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Provider;
import java.security.Principal;

@Controller
public class HomeController {

    private final Provider<ConnectionRepository> connectionRepositoryProvider;

    @Autowired
    public HomeController(Provider<ConnectionRepository> connectionRepositoryProvider) {
        this.connectionRepositoryProvider = connectionRepositoryProvider;
    }

    @RequestMapping("/")
    public String home(Principal currentUser, Model model) {
        Connection<Twitter> connection = getConnectionRepository().findPrimaryConnection(Twitter.class);
        UserProfile profile = connection.fetchUserProfile();
        model.addAttribute(profile);
        return "home";
    }

    private ConnectionRepository getConnectionRepository() {
        return connectionRepositoryProvider.get();
    }
}
