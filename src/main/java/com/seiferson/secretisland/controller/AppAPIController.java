package com.seiferson.secretisland.controller;

import com.seiferson.secretisland.model.HPost;
import com.seiferson.secretisland.model.JournalEntry;
import com.seiferson.secretisland.model.continental.Score;
import com.seiferson.secretisland.repository.HPostRepository;
import com.seiferson.secretisland.repository.JournalEntryRepository;
import com.seiferson.secretisland.repository.ScoreRepository;
import com.seiferson.secretisland.util.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class AppAPIController {

    @Autowired
    private HPostRepository hPostRepo;

    @Autowired
    private JournalEntryRepository journalRepo;

    @Autowired
    private ScoreRepository scoreRepo;

    private static final Logger logger = LoggerFactory.getLogger(AppAPIController.class);

    @GetMapping("/api/v1/continental/scores")
    public Page<Score> getAllScores(Pageable pageable) {
        return scoreRepo.findAll(pageable);
    }

    @GetMapping("/ap1/v1/continental/games")
    public List<Game> getGamesInfo(Pageable pageable) {
        List<Score> scores = scoreRepo.findAll();
        Map<String, Game> games = new HashMap<>();

        for(Score s : scores) {
            if(!games.containsKey(s.getGame())){
                Game g = new Game();
                g.setName(s.getGame());
                g.setScores(new ArrayList<>());
                g.getScores().add(s);
                games.put(s.getGame(), g);
            } else {
                games.get(s.getGame()).getScores().add(s);
            }
        }

        for(Game g: games.values()) {
            Integer min = 1000000;
            String winner = "";
            Integer total = 0;

            for(Score s: g.getScores()) {
                total += s.getTotal();
                if(s.getTotal() < min) {
                    min = s.getTotal();
                    winner = s.getPlayer();
                }
            }

            g.setWinner(winner);
            g.setWinnerScore(min);
            g.setTotalPoints(total);
        }

        return new ArrayList<>(games.values());
    }

    @PostMapping("/api/v1/continental/scores")
    public Score registerScore(@RequestBody Score score) {
        int total = 0;
        for(int i = 0; i < score.getPoints().length; i++) {
            total += score.getPoints()[i];
        }
        score.setTotal(total);
        return scoreRepo.insert(score);
    }

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

    @GetMapping("/api/v1/journal/entries")
    public Page<JournalEntry> getJournalEntries(Pageable pageable) {
        return journalRepo.findAll(pageable);
    }

    @PostMapping("/api/v1/journal/entries")
    public JournalEntry createJournalEntry(@RequestBody JournalEntry entry) {
        entry.setDate(new Date());

        return journalRepo.insert(entry);
    }
}
