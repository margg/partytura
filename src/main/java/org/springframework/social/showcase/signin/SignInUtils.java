package org.springframework.social.showcase.signin;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

public class SignInUtils {

  /**
   * Programmatically signs in the user with the given user ID.
   */
  public static void signin(String userId) {
    SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userId, null, null));
  }
}
