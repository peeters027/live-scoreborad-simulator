package pl.szmolke.service;

import pl.szmolke.database.InMemoryDB;
import pl.szmolke.model.Match;
import pl.szmolke.validator.ScoreboardValidator.ValidationResult;

import static pl.szmolke.validator.ScoreboardValidator.ValidationResult.SUCCESS;
import static pl.szmolke.validator.ScoreboardValidator.isMatchValid;
import static pl.szmolke.validator.ScoreboardValidator.isScoreInputValid;

public class ScoreboardService {

    public Match startMatch(String homeTeam, String guestTeam) {

        Match match = Match.builder()
                .homeTeam(homeTeam)
                .guestTeam(guestTeam)
                .build();

        ValidationResult result = isMatchValid(match);

        if (result != SUCCESS) {
            System.out.println(result.getMessage());
            return null;
        }

        InMemoryDB.MATCHES.add(match);
        return match;
    }

    public Match updateMatch(Integer indexFromScoreboard, int homeScore, int guestScore) {

        Match match = Match.builder()
                .homeScore(homeScore)
                .guestScore(guestScore)
                .build();
        ValidationResult result = isScoreInputValid(match);

        if (result != SUCCESS) {
            System.out.println(result.getMessage());
            return null;
        }
        Match matchToUpdate = InMemoryDB.MATCHES.get(indexFromScoreboard - 1);
        matchToUpdate.setHomeScore(match.getHomeScore());
        matchToUpdate.setGuestScore(match.getGuestScore());
        return matchToUpdate;
    }
}
