package pl.szmolke.service;

import pl.szmolke.database.InMemoryDB;
import pl.szmolke.model.Match;

import java.time.Clock;
import java.time.LocalDateTime;

public class ScoreboardService {

    public Match startMatch(String homeTeam, String guestTeam) {

        boolean atLeastOneTeamAlreadyInPlay = InMemoryDB.MATCHES
                .stream()
                .anyMatch(m -> m.getHomeTeam().equals(homeTeam)
                        || m.getHomeTeam().equals(guestTeam)
                        || m.getGuestTeam().equals(homeTeam)
                        || m.getGuestTeam().equals(guestTeam));

        if (atLeastOneTeamAlreadyInPlay) {
            System.out.println("Match was not added. At least one of the provided team is currently playing.");
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
