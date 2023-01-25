package com.seiferson.secretisland.controller;

import com.seiferson.secretisland.model.HPost;
import com.seiferson.secretisland.repository.HPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

@RestController
public class AppAPIController {

    @Autowired
    private HPostRepository hPostRepo;

    @GetMapping("/api/v1/security/userdata")
    private Map<String, Object> getGithubUserData(@AuthenticationPrincipal OAuth2User principal) {
        return principal.getAttributes();
    }

    @PostMapping("/api/v1/hPosts")
    private HPost createHomePost(@RequestBody HPost post) {
        post.setPostedDate(new Date());
        post.setUpdated(new Date());

        return hPostRepo.insert(post);
    }

    @GetMapping("/api/v1/hPosts")
    private Page<HPost> getHomePosts(Pageable pageable) {
        return hPostRepo.findAll(pageable);
    }

}