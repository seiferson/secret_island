package com.seiferson.secretisland.util;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public class Player {

    private String name;
    private Integer bestScore;
    private Integer highestScore;
    private Double averageScore;
    private Integer[] highestScoreByRound;
    private Double[] averageScoreByRound;
    @JsonIgnore
    private Integer[] lifetimePointsByRound;
    @JsonIgnore
    private Integer gamesWon;
    @JsonIgnore
    private Integer lifetimePoints;
    private Integer gamesPlayed;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBestScore() {
        return bestScore;
    }

    public void setBestScore(Integer bestScore) {
        this.bestScore = bestScore;
    }

    public Integer getHighestScore() {
        return highestScore;
    }

    public void setHighestScore(Integer highestScore) {
        this.highestScore = highestScore;
    }

    public Double getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(Double averageScore) {
        this.averageScore = averageScore;
    }

    public Integer getGamesWon() {
        return gamesWon;
    }

    public void setGamesWon(Integer gamesWon) {
        this.gamesWon = gamesWon;
    }
    public Integer getLifetimePoints() {
        return lifetimePoints;
    }

    public void setLifetimePoints(Integer lifetimePoints) {
        this.lifetimePoints = lifetimePoints;
    }

    public Integer[] getHighestScoreByRound() {
        return highestScoreByRound;
    }

    public void setHighestScoreByRound(Integer[] highestScoreByRound) {
        this.highestScoreByRound = highestScoreByRound;
    }

    public Double[] getAverageScoreByRound() {
        return averageScoreByRound;
    }

    public void setAverageScoreByRound(Double[] averageScoreByRound) {
        this.averageScoreByRound = averageScoreByRound;
    }

    public Integer getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(Integer gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public Integer[] getLifetimePointsByRound() {
        return lifetimePointsByRound;
    }

    public void setLifetimePointsByRound(Integer[] lifetimePointsByRound) {
        this.lifetimePointsByRound = lifetimePointsByRound;
    }
}
