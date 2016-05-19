package pl.edu.agh.tai.partytura;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.web.ConnectController;

@Configuration
@EnableSocial
public class TwitterConfig extends SocialConfigurerAdapter {

  @Bean
  public UserIdSource userIdSource() {
    return new UserIdSource() {
      @Override
      public String getUserId() {
        return "testuser";
      }
    };
  }

  @Bean
  public ConnectController connectController(ConnectionFactoryLocator connectionFactoryLocator, ConnectionRepository connectionRepository) {
    ConnectController controller = new ConnectController(connectionFactoryLocator, connectionRepository);
    controller.setApplicationUrl("http://localhost:8080/");
    return controller;
  }

}