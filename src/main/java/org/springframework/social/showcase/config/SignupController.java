/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.social.showcase.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.*;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.showcase.model.Attender;
import org.springframework.social.showcase.model.Institution;
import org.springframework.social.showcase.model.User;
import org.springframework.social.showcase.persistence.AttenderRepository;
import org.springframework.social.showcase.persistence.InstitutionRepository;
import org.springframework.social.showcase.signin.SignInUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;

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
    public SignupForm signupForm(WebRequest request, Model model) {
        Connection<?> connectionFromSession = signInUtils.getConnectionFromSession(request);
        return Optional.<Connection<?>>ofNullable(connectionFromSession).map(connection -> {
            SignupForm signupForm = SignupForm.fromProviderUser(connection.fetchUserProfile());
            model.addAttribute("form", signupForm);
            model.addAttribute("types", UserType.values());
            return signupForm;
        }).orElse(null);
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
