package org.springframework.social.showcase.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;
import org.springframework.social.connect.web.ProviderSignInController;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.social.showcase.signin.ImplicitSignInAdapter;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;

@Configuration
@EnableSocial
public class SocialConfig extends SocialConfigurerAdapter {

  @Autowired
  private Environment environment;

  @Autowired
  private DataSource dataSource;

  @Autowired
  private AccountImplicitConnectionSignup connectionSignup;

  @Bean(name = "usersConnectionRepository")
  @Scope(value="singleton", proxyMode=ScopedProxyMode.INTERFACES)
  @Override
  public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
    JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText());
    repository.setConnectionSignUp(connectionSignup);
    return repository;
  }

  @Bean
  public ProviderSignInController providerSignInController(
      ConnectionFactoryLocator connectionFactoryLocator, UsersConnectionRepository usersConnectionRepository) {
    return new ProviderSignInController(connectionFactoryLocator, usersConnectionRepository, signInAdapter());
  }

  @Bean
  public SignInAdapter signInAdapter() {
    return new ImplicitSignInAdapter(new HttpSessionRequestCache());
  }

/*  @Bean
  @Scope(value="singleton", proxyMode=ScopedProxyMode.INTERFACES)
  public ConnectionFactoryLocator connectionFactoryLocator() {
    ConnectionFactoryRegistry registry = new ConnectionFactoryRegistry();

    registry.addConnectionFactory(new TwitterConnectionFactory(
        environment.getProperty("twitter.consumerKey"),
        environment.getProperty("twitter.consumerSecret")));

    return registry;
  }*/
}
