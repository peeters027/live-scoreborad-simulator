package pl.szmolke.service;

import pl.szmolke.database.InMemoryDB;
import pl.szmolke.model.Match;

import java.time.Clock;
import java.time.LocalDateTime;

public class ScoreboardService {

    public Match startMatch(String homeTeam, String guestTeam) {

        Match match = Match.builder()
                .homeTeam(homeTeam)
                .guestTeam(guestTeam)
                .build();
        InMemoryDB.MATCHES.add(match);
        return match;
    }
}
