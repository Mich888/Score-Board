package com.sportradar.football;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FootballWorldCupScoreBoard {
    List<Match> matches = new ArrayList<>();

    public FootballWorldCupScoreBoard() {

    }

    public void startGame(String homeTeam, String awayTeam) {
        var match = new Match(homeTeam, awayTeam, Instant.now());
        matches.add(match);
    }

    public void finishGame(String homeTeam, String awayTeam) {
        matches.removeIf(match -> match.homeTeam.equals(homeTeam) && match.awayTeam.equals(awayTeam));
    }

    public void updateGame(String homeTeam, int homeTeamScore, String awayTeam, int awayTeamScore) {
        for (var match : matches) {
            if (match.homeTeam.equals(homeTeam) && match.awayTeam.equals(awayTeam)) {
                match.homeTeamScore = homeTeamScore;
                match.awayTeamScore = awayTeamScore;
                return;
            }
        }
    }

    public String getSummaryOfGamesByTotalScore() {
        var sorted = matches.stream().sorted(new MatchComparator()).collect(Collectors.toList());
        var sb = new StringBuilder();
        for (int i = 0; i < sorted.size(); i++) {
            sb.append(i + 1);
            sb.append(". ");
            sb.append(sorted.get(i).toString());
            if (i < sorted.size() - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    static class Match {
        String homeTeam;
        int homeTeamScore;
        String awayTeam;
        int awayTeamScore;
        Instant timeAdded;

        public Match(String homeTeam, String awayTeam, Instant timeAdded) {
            this.homeTeam = homeTeam;
            this.awayTeam = awayTeam;
            this.timeAdded = timeAdded;
            this.homeTeamScore = 0;
            this.awayTeamScore = 0;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Match match = (Match) o;

            if (homeTeamScore != match.homeTeamScore) return false;
            if (awayTeamScore != match.awayTeamScore) return false;
            if (!Objects.equals(homeTeam, match.homeTeam)) return false;
            if (!Objects.equals(awayTeam, match.awayTeam)) return false;
            return Objects.equals(timeAdded, match.timeAdded);
        }

        @Override
        public int hashCode() {
            int result = homeTeam != null ? homeTeam.hashCode() : 0;
            result = 31 * result + homeTeamScore;
            result = 31 * result + (awayTeam != null ? awayTeam.hashCode() : 0);
            result = 31 * result + awayTeamScore;
            result = 31 * result + (timeAdded != null ? timeAdded.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return homeTeam +
                    " " +
                    homeTeamScore +
                    " - " +
                    awayTeam +
                    " " +
                    awayTeamScore;
        }
    }

    static class MatchComparator implements Comparator<Match> {
        @Override
        public int compare(Match first, Match second) {
            int firstTotalScore = first.homeTeamScore + first.awayTeamScore;
            int secondTotalScore = second.homeTeamScore + second.awayTeamScore;

            if (firstTotalScore > secondTotalScore) {
                return -1;
            }
            else if (firstTotalScore < secondTotalScore) {
                return 1;
            }
            return first.timeAdded.compareTo(second.timeAdded) * (-1);
        }
    }
}
