package pl.szmolke.service;

import pl.szmolke.database.InMemoryDB;
import pl.szmolke.model.Match;
import pl.szmolke.validator.StartMatchValidator.ValidationResult;

import static pl.szmolke.validator.StartMatchValidator.ValidationResult.SUCCESS;
import static pl.szmolke.validator.StartMatchValidator.isGuestTeamAlreadyInPlay;
import static pl.szmolke.validator.StartMatchValidator.isGuestTeamNameValid;
import static pl.szmolke.validator.StartMatchValidator.isHomeTeamAlreadyInPlay;
import static pl.szmolke.validator.StartMatchValidator.isHomeTeamNameValid;

public class ScoreboardService {

    public Match startMatch(String homeTeam, String guestTeam) {

        Match match = Match.builder()
                .homeTeam(homeTeam)
                .guestTeam(guestTeam)
                .build();

        ValidationResult result = isHomeTeamNameValid()
                .and(isGuestTeamNameValid())
                .and(isHomeTeamAlreadyInPlay())
                .and(isGuestTeamAlreadyInPlay())
                .apply(match);

        if (result != SUCCESS) {
            System.out.println(result);
            return null;
        }

        InMemoryDB.MATCHES.add(match);
        return match;
    }
}
