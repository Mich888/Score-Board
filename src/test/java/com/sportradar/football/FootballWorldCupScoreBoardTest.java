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
    public void gamesInSummaryShouldBeOrderedByTotalScoreAndTimeAdded() throws InterruptedException {
        var scoreBoard = new FootballWorldCupScoreBoard();
        scoreBoard.startGame("Uruguay", "Italy");
        scoreBoard.updateGame("Uruguay", 3, "Italy", 0);
        Thread.sleep(10);
        scoreBoard.startGame("Spain", "Brazil");
        scoreBoard.updateGame("Spain", 0, "Brazil", 3);
        Thread.sleep(10);
        scoreBoard.startGame("Poland", "USA");
        scoreBoard.updateGame("Poland", 2, "USA", 1);
        Thread.sleep(10);
        scoreBoard.startGame("England", "Germany");
        scoreBoard.updateGame("England", 1, "Germany", 0);
        var summary = scoreBoard.getSummaryOfGamesByTotalScore();
        var expected = "1. Poland 2 - USA 1\n" + "2. Spain 0 - Brazil 3\n" + "3. Uruguay 3 - Italy 0\n" + "4. England 1 - Germany 0";
        assertEquals(expected, summary);
    }
}