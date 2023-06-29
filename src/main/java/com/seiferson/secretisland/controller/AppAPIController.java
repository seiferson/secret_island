package com.seiferson.secretisland.controller;

import com.seiferson.secretisland.model.HPost;
import com.seiferson.secretisland.model.JournalEntry;
import com.seiferson.secretisland.model.continental.Score;
import com.seiferson.secretisland.repository.HPostRepository;
import com.seiferson.secretisland.repository.JournalEntryRepository;
import com.seiferson.secretisland.repository.ScoreRepository;
import com.seiferson.secretisland.util.Game;
import com.seiferson.secretisland.util.Player;
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

    @GetMapping("/api/v1/continental/players")
    public List<Player> getPlayerStats(Pageable pageable) {
        List<Score> scores = scoreRepo.findAll();
        Map<String, Player> players = new HashMap<>();

        for(Score s : scores) {
            if(!players.containsKey(s.getPlayer())){
                Player p = new Player();
                p.setAverageScore(0.0);
                p.setBestScore(1000000);
                p.setHighestScore(0);
                p.setGamesPlayed(0);
                p.setGamesWon(0);
                p.setName(s.getPlayer());
                p.setLifetimePoints(0);
                p.setLifetimePointsByRound(new Integer[7]);
                p.setHighestScoreByRound(new Integer[7]);
                p.setAverageScoreByRound(new Double[7]);

                for(int i =0; i<7; i++) {
                    p.getHighestScoreByRound()[i] = 0;
                    p.getAverageScoreByRound()[i] = 0.0;
                    p.getLifetimePointsByRound()[i] = 0;
                }

                players.put(s.getPlayer(), p);
            }

            Player current = players.get(s.getPlayer());
            current.setLifetimePoints(current.getLifetimePoints()+s.getTotal());
            current.setGamesPlayed(current.getGamesPlayed() + 1);

            if(s.getTotal() > current.getHighestScore()) {
                current.setHighestScore(s.getTotal());
            }

            if(s.getTotal() < current.getBestScore()) {
                current.setBestScore(s.getTotal());
            }

            Integer[] points = s.getPoints();
            for(int i = 0; i < 7; i++) {
                if(points[i] > current.getHighestScoreByRound()[i]) {
                    current.getHighestScoreByRound()[i] = points[i];
                }
                current.getLifetimePointsByRound()[i] = current.getLifetimePointsByRound()[i] + points[i];
            }

        }

        for(Player p : players.values()) {
            p.setAverageScore(new Double(p.getLifetimePoints())/p.getGamesPlayed());
            for(int i = 0; i < 7; i++) {
                p.getAverageScoreByRound()[i] = new Double(p.getLifetimePointsByRound()[i]) / p.getGamesPlayed();
            }
        }

        return new ArrayList<>(players.values());
    }

    @GetMapping("/api/v1/continental/games")
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
