import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.szmolke.database.InMemoryDB;
import pl.szmolke.model.Match;
import pl.szmolke.service.ScoreboardService;

import java.util.Arrays;

class ScoreboardServiceTest {

    private ScoreboardService scoreboardService;

    @BeforeEach
    void setUp() {
        scoreboardService = new ScoreboardService();
        initInMemoryDb();
    }

    private void initInMemoryDb() {
        InMemoryDB.MATCHES.addAll(Arrays.asList(
                Match.builder()
                        .homeTeam("Spain")
                        .guestTeam("Brazil")
                        .homeScore(10)
                        .guestScore(2)
                        .build(),
                Match.builder()
                        .homeTeam("Mexico")
                        .guestTeam("Canada")
                        .homeScore(0)
                        .guestScore(5)
                        .build()
        ));
    }

    @Test
    void itShouldStartMatchWithInitialScoreWhenTeamsAreNotCurrentlyPlaying() {

        // given
        String homeTeam = "Poland";
        String guestTeam = "Germany";
        int numberOfMatchesInPlayBeforeAdding = InMemoryDB.MATCHES.size();

        // when
        Match startedMatch = scoreboardService.startMatch(homeTeam, guestTeam);

        // then
        Assertions.assertEquals(homeTeam, startedMatch.getHomeTeam());
        Assertions.assertEquals(guestTeam, startedMatch.getGuestTeam());
        Assertions.assertEquals(0, startedMatch.getHomeScore());
        Assertions.assertEquals(0, startedMatch.getGuestScore());
        Assertions.assertEquals(numberOfMatchesInPlayBeforeAdding + 1, InMemoryDB.MATCHES.size());
    }

    @Test
    void itShouldNorStartMatchWhenAtLestOneTeamIsCurrentlyPlaying() {

        // given
        String homeTeam = "Spain";
        String guestTeam = "Germany";
        int numberOfMatchesInPlayBeforeAdding = InMemoryDB.MATCHES.size();

        // when
        Match startedMatch = scoreboardService.startMatch(homeTeam, guestTeam);

        // then
        Assertions.assertNull(startedMatch);
        Assertions.assertEquals(numberOfMatchesInPlayBeforeAdding, InMemoryDB.MATCHES.size());
    }
}
