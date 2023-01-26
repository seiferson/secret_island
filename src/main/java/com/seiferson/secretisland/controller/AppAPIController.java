package com.seiferson.secretisland.controller;

import com.seiferson.secretisland.model.HPost;
import com.seiferson.secretisland.repository.HPostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(AppAPIController.class);

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

    @GetMapping("/log")
    public String log() {
        logger.trace("This is a TRACE level message");
        logger.debug("This is a DEBUG level message");
        logger.info("This is an INFO level message");
        logger.warn("This is a WARN level message");
        logger.error("This is an ERROR level message");
        return "See the log for details";
    }

}
