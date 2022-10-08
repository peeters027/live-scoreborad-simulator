package service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.szmolke.exception.IndexFormatException;
import pl.szmolke.exception.ScoreFormatException;
import pl.szmolke.exception.TeamNameFormatException;
import pl.szmolke.model.Match;
import pl.szmolke.service.ScoreboardService;
import pl.szmolke.validator.ValidationResult;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static pl.szmolke.database.InMemoryDB.MATCHES;
import static pl.szmolke.validator.ValidationResult.GUEST_TEAM_ALREADY_IN_PLAY;
import static pl.szmolke.validator.ValidationResult.GUEST_TEAM_NAME_INVALID;
import static pl.szmolke.validator.ValidationResult.GUEST_TEAM_SCORE_INVALID;
import static pl.szmolke.validator.ValidationResult.HOME_TEAM_ALREADY_IN_PLAY;
import static pl.szmolke.validator.ValidationResult.HOME_TEAM_NAME_INVALID;
import static pl.szmolke.validator.ValidationResult.HOME_TEAM_SCORE_INVALID;

class ScoreboardServiceTest {

    private ScoreboardService scoreboardService;

    @BeforeEach
    void setUp() {
        scoreboardService = new ScoreboardService();
        initInMemoryDb();
    }

    @Test
    void itShouldStartMatchWithInitialScoreWhenTeamsAreNotCurrentlyPlaying() throws TeamNameFormatException {

        // given
        String homeTeam = "Poland";
        String guestTeam = "Portugal";
        int numberOfMatchesInPlayBeforeAdding = MATCHES.size();

        // when
        Match startedMatch = scoreboardService.startMatch(homeTeam, guestTeam);

        // then
        assertEquals(homeTeam, startedMatch.getHomeTeam());
        assertEquals(guestTeam, startedMatch.getGuestTeam());
        assertEquals(0, startedMatch.getHomeScore());
        assertEquals(0, startedMatch.getGuestScore());
        assertEquals(numberOfMatchesInPlayBeforeAdding + 1, MATCHES.size());
    }

    @Test
    void itShouldNotStartMatchWhenHomeTeamIsCurrentlyPlaying() {

        // given
        String homeTeam = "Spain";
        String guestTeam = "Portugal";
        int numberOfMatchesInPlayBeforeAdding = MATCHES.size();

        // when
        TeamNameFormatException thrown = assertThrows(TeamNameFormatException.class, () -> scoreboardService.startMatch(homeTeam, guestTeam));


        // then
        assertEquals(numberOfMatchesInPlayBeforeAdding, MATCHES.size());
        assertEquals(HOME_TEAM_ALREADY_IN_PLAY.getMessage(), thrown.getMessage());
    }

    @Test
    void itShouldNotStartMatchWhenGuestTeamIsCurrentlyPlaying() {

        // given
        String homeTeam = "Portugal";
        String guestTeam = "Spain";
        int numberOfMatchesInPlayBeforeAdding = MATCHES.size();

        // when
        TeamNameFormatException thrown = assertThrows(TeamNameFormatException.class, () -> scoreboardService.startMatch(homeTeam, guestTeam));


        // then
        assertEquals(numberOfMatchesInPlayBeforeAdding, MATCHES.size());
        assertEquals(GUEST_TEAM_ALREADY_IN_PLAY.getMessage(), thrown.getMessage());
    }

    @Test
    void itShouldNotStartMatchWhenBothTeamsAreCurrentlyPlaying() {

        // given
        String homeTeam = "Mexico";
        String guestTeam = "Spain";
        int numberOfMatchesInPlayBeforeAdding = MATCHES.size();

        // when
        TeamNameFormatException thrown = assertThrows(TeamNameFormatException.class, () -> scoreboardService.startMatch(homeTeam, guestTeam));


        // then
        assertEquals(numberOfMatchesInPlayBeforeAdding, MATCHES.size());
        assertEquals(HOME_TEAM_ALREADY_IN_PLAY.getMessage(), thrown.getMessage());
    }

    @Test
    void itShouldNotStartMatchWhenHomeTeamNameIsEmpty() {

        // given
        String homeTeam = "";
        String guestTeam = "Portugal";
        int numberOfMatchesInPlayBeforeAdding = MATCHES.size();

        // when
        TeamNameFormatException thrown = assertThrows(TeamNameFormatException.class, () -> scoreboardService.startMatch(homeTeam, guestTeam));


        // then
        assertEquals(numberOfMatchesInPlayBeforeAdding, MATCHES.size());
        assertEquals(HOME_TEAM_NAME_INVALID.getMessage(), thrown.getMessage());
    }

    @Test
    void itShouldNotStartMatchWhenHomeTeamNameIsNull() {

        // given
        String guestTeam = "Portugal";
        int numberOfMatchesInPlayBeforeAdding = MATCHES.size();

        // when
        TeamNameFormatException thrown = assertThrows(TeamNameFormatException.class, () -> scoreboardService.startMatch(null, guestTeam));


        // then
        assertEquals(numberOfMatchesInPlayBeforeAdding, MATCHES.size());
        assertEquals(HOME_TEAM_NAME_INVALID.getMessage(), thrown.getMessage());
    }

    @Test
    void itShouldNotStartMatchWhenGuestTeamNameIsEmpty() {

        // given
        String homeTeam = "Portugal";
        String guestTeam = "";
        int numberOfMatchesInPlayBeforeAdding = MATCHES.size();

        // when
        TeamNameFormatException thrown = assertThrows(TeamNameFormatException.class,
                () -> scoreboardService.startMatch(homeTeam, guestTeam));


        // then
        assertEquals(numberOfMatchesInPlayBeforeAdding, MATCHES.size());
        assertEquals(GUEST_TEAM_NAME_INVALID.getMessage(), thrown.getMessage());
    }

    @Test
    void itShouldUpdateMatchWhenScoresAreCorrect() throws ScoreFormatException, IndexFormatException {

        // given
        Integer index = 1;
        int homeScore = 10;
        int guestScore = 20;

        // when
        Match updatedMatch = scoreboardService.updateMatch(index, homeScore, guestScore);

        // then
        assertEquals(updatedMatch.getHomeScore(), MATCHES.get(0).getHomeScore());
        assertEquals(updatedMatch.getGuestScore(), MATCHES.get(0).getGuestScore());
    }

    @Test
    void itShouldNotUpdateMatchWhenGuestScoreIsNotCorrect() {

        // given
        int index = 1;
        int homeScore = 100;
        int guestScore = -2;

        // when
        ScoreFormatException thrown = assertThrows(ScoreFormatException.class,
                () -> scoreboardService.updateMatch(index, homeScore, guestScore));

        // then
        assertEquals(10, MATCHES.get(0).getHomeScore());
        assertEquals(2, MATCHES.get(0).getGuestScore());
        assertEquals(GUEST_TEAM_SCORE_INVALID.getMessage(), thrown.getMessage());

    }

    @Test
    void itShouldNotUpdateMatchWhenHomeScoreIsNotCorrect() {

        // given
        int index = 1;
        int homeScore = -2;
        int guestScore = 10;

        // when
        ScoreFormatException thrown = assertThrows(ScoreFormatException.class,
                () -> scoreboardService.updateMatch(index, homeScore, guestScore));

        // then
        assertEquals(10, MATCHES.get(0).getHomeScore());
        assertEquals(2, MATCHES.get(0).getGuestScore());
        assertEquals(HOME_TEAM_SCORE_INVALID.getMessage(), thrown.getMessage());
    }

    @Test
    void itShouldRemoveMatchWhenIndexIsCorrect() throws IndexFormatException {

        // given
        int numberOfMatchesInPlayBeforeAdding = MATCHES.size();
        int indexFromScoreboard = 1;
        Match matchExpectedToBeRemoved = MATCHES.get(0);

        // when
        Match removedMatch = scoreboardService.removeMatch(indexFromScoreboard);

        // then
        assertEquals(numberOfMatchesInPlayBeforeAdding - 1, MATCHES.size());
        assertEquals(matchExpectedToBeRemoved, removedMatch);
    }

    @Test
    void itShouldNotRemoveMatchWhenIndexIsNotCorrect() {

        // given
        int numberOfMatchesInPlayBeforeAdding = MATCHES.size();
        int indexFromScoreboard = -1;

        // when
        IndexFormatException indexFormatException = assertThrows(IndexFormatException.class,
                () -> scoreboardService.removeMatch(indexFromScoreboard));

        // then
        assertEquals(numberOfMatchesInPlayBeforeAdding, MATCHES.size());
        assertEquals(ValidationResult.INDEX_FROM_SCOREBOARD_INVALID.getMessage(), indexFormatException.getMessage());
    }

    private void initInMemoryDb() {
        MATCHES = new ArrayList<>(Arrays.asList(
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
