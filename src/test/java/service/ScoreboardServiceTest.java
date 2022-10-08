package service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.szmolke.database.InMemoryDB;
import pl.szmolke.exception.ScoreFormatException;
import pl.szmolke.model.Match;
import pl.szmolke.service.ScoreboardService;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static pl.szmolke.validator.ScoreboardValidator.ValidationResult.GUEST_TEAM_SCORE_NOT_VALID;
import static pl.szmolke.validator.ScoreboardValidator.ValidationResult.HOME_TEAM_SCORE_NOT_VALID;

class ScoreboardServiceTest {

    private ScoreboardService scoreboardService;

    @BeforeEach
    void setUp() {
        scoreboardService = new ScoreboardService();
        initInMemoryDb();
    }

    @Test
    void itShouldStartMatchWithInitialScoreWhenTeamsAreNotCurrentlyPlaying() {

        // given
        String homeTeam = "Poland";
        String guestTeam = "Portugal";
        int numberOfMatchesInPlayBeforeAdding = InMemoryDB.MATCHES.size();

        // when
        Match startedMatch = scoreboardService.startMatch(homeTeam, guestTeam);

        // then
        assertEquals(homeTeam, startedMatch.getHomeTeam());
        assertEquals(guestTeam, startedMatch.getGuestTeam());
        assertEquals(0, startedMatch.getHomeScore());
        assertEquals(0, startedMatch.getGuestScore());
        assertEquals(numberOfMatchesInPlayBeforeAdding + 1, InMemoryDB.MATCHES.size());
    }

    @Test
    void itShouldNotStartMatchWhenAtHomeTeamIsCurrentlyPlaying() {

        // given
        String homeTeam = "Spain";
        String guestTeam = "Portugal";
        int numberOfMatchesInPlayBeforeAdding = InMemoryDB.MATCHES.size();

        // when
        Match startedMatch = scoreboardService.startMatch(homeTeam, guestTeam);

        // then
        assertNull(startedMatch);
        assertEquals(numberOfMatchesInPlayBeforeAdding, InMemoryDB.MATCHES.size());
    }

    @Test
    void itShouldNotStartMatchWhenAtGuestTeamIsCurrentlyPlaying() {

        // given
        String homeTeam = "Portugal";
        String guestTeam = "Spain";
        int numberOfMatchesInPlayBeforeAdding = InMemoryDB.MATCHES.size();

        // when
        Match startedMatch = scoreboardService.startMatch(homeTeam, guestTeam);

        // then
        assertNull(startedMatch);
        assertEquals(numberOfMatchesInPlayBeforeAdding, InMemoryDB.MATCHES.size());
    }

    @Test
    void itShouldNotStartMatchWhenBothTeamsAreCurrentlyPlaying() {

        // given
        String homeTeam = "Mexico";
        String guestTeam = "Spain";
        int numberOfMatchesInPlayBeforeAdding = InMemoryDB.MATCHES.size();

        // when
        Match startedMatch = scoreboardService.startMatch(homeTeam, guestTeam);

        // then
        assertNull(startedMatch);
        assertEquals(numberOfMatchesInPlayBeforeAdding, InMemoryDB.MATCHES.size());
    }

    @Test
    void itShouldNotStartMatchWhenHomeTeamNameIsEmpty() {

        // given
        String homeTeam = "";
        String guestTeam = "Portugal";
        int numberOfMatchesInPlayBeforeAdding = InMemoryDB.MATCHES.size();

        // when
        Match startedMatch = scoreboardService.startMatch(homeTeam, guestTeam);

        // then
        assertNull(startedMatch);
        assertEquals(numberOfMatchesInPlayBeforeAdding, InMemoryDB.MATCHES.size());
    }

    @Test
    void itShouldNotStartMatchWhenHomeTeamNameIsNull() {

        // given
        String homeTeam = null;
        String guestTeam = "Portugal";
        int numberOfMatchesInPlayBeforeAdding = InMemoryDB.MATCHES.size();

        // when
        Match startedMatch = scoreboardService.startMatch(homeTeam, guestTeam);

        // then
        assertNull(startedMatch);
        assertEquals(numberOfMatchesInPlayBeforeAdding, InMemoryDB.MATCHES.size());
    }

    @Test
    void itShouldNotStartMatchWhenGuestTeamNameIsEmpty() {

        // given
        String homeTeam = "Portugal";
        String guestTeam = "";
        int numberOfMatchesInPlayBeforeAdding = InMemoryDB.MATCHES.size();

        // when
        Match startedMatch = scoreboardService.startMatch(homeTeam, guestTeam);

        // then
        assertNull(startedMatch);
        assertEquals(numberOfMatchesInPlayBeforeAdding, InMemoryDB.MATCHES.size());
    }

    @Test
    void itShouldUpdateMatchWhenScoresAreCorrect() throws ScoreFormatException {

        // given
        Integer index = 1;
        int homeScore = 10;
        int guestScore = 20;

        // when
        Match updatedMatch = scoreboardService.updateMatch(index, homeScore, guestScore);

        // then
        assertEquals(updatedMatch.getHomeScore(), InMemoryDB.MATCHES.get(0).getHomeScore());
        assertEquals(updatedMatch.getGuestScore(), InMemoryDB.MATCHES.get(0).getGuestScore());
    }

    @Test
    void itShouldNotUpdateMatchWhenGuestScoreIsNotCorrect() {

        // given
        int index = 1;
        int homeScore = 100;
        int guestScore = -2;

        // when
        ScoreFormatException thrown = assertThrows(ScoreFormatException.class, () -> scoreboardService.updateMatch(index, homeScore, guestScore));

        // then
        assertEquals(10, InMemoryDB.MATCHES.get(0).getHomeScore());
        assertEquals(2, InMemoryDB.MATCHES.get(0).getGuestScore());
        assertEquals(GUEST_TEAM_SCORE_NOT_VALID.getMessage(), thrown.getMessage());

    }

    @Test
    void itShouldNotUpdateMatchWhenHomeScoreIsNotCorrect() {

        // given
        int index = 1;
        int homeScore = -2;
        int guestScore = 10;

        // when
        ScoreFormatException thrown = assertThrows(ScoreFormatException.class, () -> scoreboardService.updateMatch(index, homeScore, guestScore));

        // then
        assertEquals(10, InMemoryDB.MATCHES.get(0).getHomeScore());
        assertEquals(2, InMemoryDB.MATCHES.get(0).getGuestScore());
        assertEquals(HOME_TEAM_SCORE_NOT_VALID.getMessage(), thrown.getMessage());
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

}
