package pl.szmolke.service;

import pl.szmolke.database.InMemoryDB;
import pl.szmolke.exception.ScoreFormatException;
import pl.szmolke.exception.TeamNameFormatException;
import pl.szmolke.model.Match;
import pl.szmolke.validator.ScoreboardValidator.ValidationResult;

import static pl.szmolke.validator.ScoreboardValidator.ValidationResult.SUCCESS;
import static pl.szmolke.validator.ScoreboardValidator.isMatchValid;
import static pl.szmolke.validator.ScoreboardValidator.isScoreValid;

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

        InMemoryDB.MATCHES.add(match);
        return match;
    }

    public Match updateMatch(Integer indexFromScoreboard, int homeScore, int guestScore) throws ScoreFormatException {

        ValidationResult result = isScoreValid(homeScore, guestScore);

        if (result != SUCCESS) {
            throw new ScoreFormatException(result.getMessage());
        }
        Match matchToUpdate = InMemoryDB.MATCHES.get(indexFromScoreboard - 1);
        matchToUpdate.setHomeScore(homeScore);
        matchToUpdate.setGuestScore(guestScore);
        return matchToUpdate;
    }
}
