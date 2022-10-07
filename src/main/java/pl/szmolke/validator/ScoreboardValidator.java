package pl.szmolke.validator;

import pl.szmolke.database.InMemoryDB;

public class ScoreboardValidator {

    public static boolean validateStartMatch(String homeTeam, String guestTeam) {

        if (homeTeam == null
                || homeTeam.isEmpty()
                || guestTeam == null
                || guestTeam.isEmpty()) {
            return false;
        }

        boolean atLeastOneTeamAlreadyInPlay = InMemoryDB.MATCHES
                .stream()
                .anyMatch(m -> m.getHomeTeam().equals(homeTeam)
                        || m.getHomeTeam().equals(guestTeam)
                        || m.getGuestTeam().equals(homeTeam)
                        || m.getGuestTeam().equals(guestTeam));

        return !atLeastOneTeamAlreadyInPlay;
    }
}
