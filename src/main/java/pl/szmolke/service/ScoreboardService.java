package pl.szmolke.service;

import pl.szmolke.database.InMemoryDB;
import pl.szmolke.model.Match;
import pl.szmolke.validator.ScoreboardValidator;

public class ScoreboardService {

    public Match startMatch(String homeTeam, String guestTeam) {

        if (!ScoreboardValidator.validateStartMatch(homeTeam, guestTeam)) {
            return null;
        }

        Match match = Match.builder()
                .homeTeam(homeTeam)
                .guestTeam(guestTeam)
                .build();
        InMemoryDB.MATCHES.add(match);
        return match;
    }
}
