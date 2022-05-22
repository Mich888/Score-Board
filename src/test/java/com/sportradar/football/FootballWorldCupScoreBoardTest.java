package com.sportradar.football;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FootballWorldCupScoreBoardTest {
    @Test
    public void noGamesAfterInitializingWorldCup() {
        var scoreBoard = new FootballWorldCupScoreBoard();
        var summary = scoreBoard.getSummaryOfGamesByTotalScore();
        assertTrue(summary.isEmpty());
    }

    @Test
    public void scoreIs00WhenAfterStartingGame() {
        var scoreBoard = new FootballWorldCupScoreBoard();
        scoreBoard.startGame("Uruguay", "Italy");
        var summary = scoreBoard.getSummaryOfGamesByTotalScore();
        assertEquals("1. Uruguay 0 - Italy 0", summary);
    }

    @Test
    public void finishingGameShouldRemoveItFromScoreBoard() {
        var scoreBoard = new FootballWorldCupScoreBoard();
        scoreBoard.startGame("Uruguay", "Italy");
        scoreBoard.finishGame("Uruguay", "Italy");
        var summary = scoreBoard.getSummaryOfGamesByTotalScore();
        assertTrue(summary.isEmpty());
    }

    @Test
    public void testUpdatingScore() {
        var scoreBoard = new FootballWorldCupScoreBoard();
        scoreBoard.startGame("Uruguay", "Italy");
        scoreBoard.updateGame("Uruguay", 3, "Italy", 0);
        var summary = scoreBoard.getSummaryOfGamesByTotalScore();
        assertEquals("1. Uruguay 3 - Italy 0", summary);
    }

    @Test
    public void gamesInSummaryShouldBeOrderedByTotalScoreAndTimeAdded() {
        var scoreBoard = new FootballWorldCupScoreBoard();
        scoreBoard.startGame("Uruguay", "Italy");
        scoreBoard.updateGame("Uruguay", 3, "Italy", 0);
        scoreBoard.startGame("Spain", "Brazil");
        scoreBoard.updateGame("Spain", 0, "Brazil", 3);
        var summary = scoreBoard.getSummaryOfGamesByTotalScore();
        var expected = "1. Spain 0 - Brazil 3\n" + "2. Uruguay 3 - Italy 0";
        assertEquals(expected, summary);
    }
}