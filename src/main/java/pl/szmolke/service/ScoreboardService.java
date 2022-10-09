package pl.szmolke.service;

import pl.szmolke.exception.IndexFormatException;
import pl.szmolke.exception.ScoreFormatException;
import pl.szmolke.exception.TeamNameFormatException;
import pl.szmolke.model.Match;
import pl.szmolke.validator.ValidationResult;

import static pl.szmolke.database.InMemoryDB.MATCHES;
import static pl.szmolke.validator.ScoreboardValidator.isMatchValid;
import static pl.szmolke.validator.ScoreboardValidator.isScoreValid;
import static pl.szmolke.validator.ValidationResult.SUCCESS;

public class ScoreboardService {

    public Match startMatch(String homeTeam, String guestTeam) throws TeamNameFormatException {

        Match match = Match.builder()
                .homeTeam(homeTeam)
                .guestTeam(guestTeam)
                .build();

        ValidationResult result = isMatchValid(match);

        if (result != SUCCESS) {
            throw new TeamNameFormatException(result.getMessage());
        }

        MATCHES.add(match);
        return match;
    }

    public Match updateMatch(Integer indexFromScoreboard, int homeScore, int guestScore) throws ScoreFormatException, IndexFormatException {

        ValidationResult scoreValidationResult = isScoreValid(homeScore, guestScore);
        if (scoreValidationResult != SUCCESS) {
            throw new ScoreFormatException(scoreValidationResult.getMessage());
        }

        Match matchToUpdate = MATCHES.get(indexFromScoreboard - 1);
        matchToUpdate.setHomeScore(homeScore);
        matchToUpdate.setGuestScore(guestScore);
        return matchToUpdate;
    }

    public Match removeMatch(Integer indexFromScoreboard) throws IndexFormatException {

        return MATCHES.remove(indexFromScoreboard - 1);
    }

    public void getSummaryOfGames() {

        MATCHES.forEach(match ->
                System.out.printf("%d. %s %d - %s %d %n",
                        MATCHES.indexOf(match) + 1,
                        match.getHomeTeam(),
                        match.getHomeScore(),
                        match.getGuestTeam(),
                        match.getGuestScore()
                ));
    }
}
