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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.env.Environment;
import org.springframework.social.connect.*;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.showcase.model.Attender;
import org.springframework.social.showcase.model.Institution;
import org.springframework.social.showcase.model.User;
import org.springframework.social.showcase.persistence.AttenderRepository;
import org.springframework.social.showcase.persistence.InstitutionRepository;
import org.springframework.social.showcase.signin.SignInUtils;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;

@Controller
public class SignupController {

    private ProviderSignInUtils providerSignInUtils;

    @Autowired
    private AttenderRepository attendeRepository;

    @Autowired
    private InstitutionRepository institutionRepository;

    @Autowired
    public SignupController(ConnectionFactoryLocator connectionFactoryLocator,
                            UsersConnectionRepository connectionRepository) {
        this.providerSignInUtils = new ProviderSignInUtils(connectionFactoryLocator, connectionRepository);
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public SignupForm signupForm(WebRequest request, Model model) {
        Connection<?> connection = providerSignInUtils.getConnectionFromSession(request);
        SignupForm signupForm;
        if (connection != null) {
            signupForm = SignupForm.fromProviderUser(connection.fetchUserProfile());
        } else {
            return null;
        }
        model.addAttribute("form", signupForm);
        model.addAttribute("types", UserType.values());
        return signupForm;
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signup(@Valid SignupForm form, BindingResult formBinding, WebRequest request) {
        if (formBinding.hasErrors()) {
            return "/error";
        }
        User user = createAccount(form, formBinding);
        if (user != null) {
            SignInUtils.signin(String.valueOf(user.getTwitterId()));
            providerSignInUtils.doPostSignUp(String.valueOf(user.getTwitterId()), request);
            return "redirect:/";
        }
        return "/error";
    }

    private User createAccount(SignupForm form, BindingResult formBinding) {
        switch (form.getType()) {
            case ATTENDER:
                return attendeRepository.insert(new Attender(form.getUsername()));
            case INSTITUTION:
                return institutionRepository.insert(new Institution(form.getUsername()));
        }
        return null;
    }
}
