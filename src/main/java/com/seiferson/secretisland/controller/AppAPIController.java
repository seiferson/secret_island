package com.seiferson.secretisland.controller;

import com.seiferson.secretisland.model.Letter;
import com.seiferson.secretisland.repository.LetterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AppAPIController {

    @Autowired
    private LetterRepository letterRepo;

    @GetMapping("/api/v1/security/userdata")
    private Map<String, Object> getGithubUserData(@AuthenticationPrincipal OAuth2User principal) {
        return principal.getAttributes();
    }

    @GetMapping("/api/v1/emptyletter")
    private Letter createEmptyLetter () {
        Letter l = new Letter();
        l.setMessage("Empty");
        letterRepo.insert(l);


        return l;
    }
}
