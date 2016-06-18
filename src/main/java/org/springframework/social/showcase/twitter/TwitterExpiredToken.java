package org.springframework.social.showcase.twitter;

import org.springframework.social.ExpiredAuthorizationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TwitterExpiredToken {

    @RequestMapping("/twitter/expired")
    public void simulateExpiredToken() {
        throw new ExpiredAuthorizationException("twitter");
    }

}
