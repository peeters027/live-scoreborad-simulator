package validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.szmolke.database.InMemoryDB;
import pl.szmolke.model.Match;
import pl.szmolke.validator.ScoreboardValidator;
import pl.szmolke.validator.ScoreboardValidator.ValidationResult;

import java.util.Arrays;

import static pl.szmolke.validator.ScoreboardValidator.ValidationResult.GUEST_TEAM_ALREADY_IN_PLAY;
import static pl.szmolke.validator.ScoreboardValidator.ValidationResult.GUEST_TEAM_NAME_INVALID;
import static pl.szmolke.validator.ScoreboardValidator.ValidationResult.GUEST_TEAM_SCORE_NOT_VALID;
import static pl.szmolke.validator.ScoreboardValidator.ValidationResult.HOME_TEAM_ALREADY_IN_PLAY;
import static pl.szmolke.validator.ScoreboardValidator.ValidationResult.HOME_TEAM_NAME_INVALID;
import static pl.szmolke.validator.ScoreboardValidator.ValidationResult.HOME_TEAM_SCORE_NOT_VALID;
import static pl.szmolke.validator.ScoreboardValidator.ValidationResult.SUCCESS;

public class ScoreboardValidatorTest {

    @BeforeEach
    void setUp() {
        initInMemoryDb();
    }

    @Test
    void itShouldValidateHomeTeamNameInputWhenHomeTeamNameIsCorrect() {

        // given
        String homeTeam = "Poland";
        Match match = Match.builder().homeTeam(homeTeam).build();

        // when
        ValidationResult result = ScoreboardValidator.isHomeTeamNameValid().apply(match);

        // then
        Assertions.assertEquals(SUCCESS.getMessage(), result.getMessage());
    }

    @Test
    void itShouldValidateHomeTeamNameInputWhenHomeTeamNameIsEmpty() {

        // given
        String homeTeam = "";
        Match match = Match.builder().homeTeam(homeTeam).build();

        // when
        ValidationResult result = ScoreboardValidator.isHomeTeamNameValid().apply(match);

        // then
        Assertions.assertEquals(HOME_TEAM_NAME_INVALID.getMessage(), result.getMessage());
    }

    @Test
    void itShouldValidateHomeTeamNameInputWhenHomeTeamNameIsNull() {

        // given
        Match match = Match.builder().homeTeam(null).build();

        // when
        ValidationResult result = ScoreboardValidator.isHomeTeamNameValid().apply(match);

        // then
        Assertions.assertEquals(HOME_TEAM_NAME_INVALID.getMessage(), result.getMessage());
    }

    @Test
    void itShouldValidateGuestTeamNameInputWhenGuestTeamNameIsCorrect() {

        // given
        String guestTeam = "Poland";
        Match match = Match.builder().guestTeam(guestTeam).build();

        // when
        ValidationResult result = ScoreboardValidator.isGuestTeamNameValid().apply(match);

        // then
        Assertions.assertEquals(SUCCESS.getMessage(), result.getMessage());
    }

    @Test
    void itShouldValidateGuestTeamNameInputWhenGuestTeamNameIsEmpty() {

        // given
        String guestTeam = "";
        Match match = Match.builder().guestTeam(guestTeam).build();

        // when
        ValidationResult result = ScoreboardValidator.isGuestTeamNameValid().apply(match);

        // then
        Assertions.assertEquals(GUEST_TEAM_NAME_INVALID.getMessage(), result.getMessage());
    }

    @Test
    void itShouldValidateGuestTeamNameInputWhenGuestTeamNameIsNull() {

        // given
        Match match = Match.builder().guestTeam(null).build();

        // when
        ValidationResult result = ScoreboardValidator.isGuestTeamNameValid().apply(match);

        // then
        Assertions.assertEquals(GUEST_TEAM_NAME_INVALID.getMessage(), result.getMessage());
    }

    @Test
    void itShouldValidateHomeTeamNameInputWhenHomeTeamIsCurrentlyPlayingMatch() {

        // given
        String homeTeam = "";
        Match match = Match.builder().homeTeam(homeTeam).build();

        // when
        ValidationResult result = ScoreboardValidator.isHomeTeamNameValid().apply(match);

        // then
        Assertions.assertEquals(HOME_TEAM_NAME_INVALID.getMessage(), result.getMessage());
    }

    @Test
    void itShouldValidateHomeTeamWhenHomeTeamIsCurrentlyPlayingAnotherMatch() {

        // given
        String homeTeam = "Mexico";
        String guestTeam = "Poland";
        Match match = Match.builder().homeTeam(homeTeam).guestTeam(guestTeam).build();

        // when
        ValidationResult result = ScoreboardValidator.isHomeTeamAlreadyInPlay().apply(match);

        // then
        Assertions.assertEquals(HOME_TEAM_ALREADY_IN_PLAY.getMessage(), result.getMessage());
    }

    @Test
    void itShouldValidateGuestTeamWhenGuestTeamIsCurrentlyPlayingAnotherMatch() {

        // given
        String homeTeam = "Poland";
        String guestTeam = "Mexico";
        Match match = Match.builder().homeTeam(homeTeam).guestTeam(guestTeam).build();

        // when
        ValidationResult result = ScoreboardValidator.isGuestTeamAlreadyInPlay().apply(match);

        // then
        Assertions.assertEquals(GUEST_TEAM_ALREADY_IN_PLAY.getMessage(), result.getMessage());
    }

    @Test
    void itShouldValidateHomeScoreWhenHomeScoreIsCorrect() {

        // given
        int homeScore = 2;
        Match match = Match.builder().homeScore(homeScore).build();

        // when
        ValidationResult result = ScoreboardValidator.isHomeScoreValid(homeScore);

        // then
        Assertions.assertEquals(SUCCESS.getMessage(), result.getMessage());
    }

    @Test
    void itShouldValidateHomeScoreWhenHomeScoreIsNotCorrect() {

        // given
        int homeScore = -2;
        Match match = Match.builder().homeScore(homeScore).build();

        // when
        ValidationResult result = ScoreboardValidator.isHomeScoreValid(homeScore);

        // then
        Assertions.assertEquals(HOME_TEAM_SCORE_NOT_VALID.getMessage(), result.getMessage());
    }

    @Test
    void itShouldValidateGuestScoreWhenGuestScoreIsCorrect() {

        // given
        int guestScore = 2;
        Match match = Match.builder().guestScore(guestScore).build();

        // when
        ValidationResult result = ScoreboardValidator.isGuestScoreValid(guestScore);

        // then
        Assertions.assertEquals(SUCCESS.getMessage(), result.getMessage());
    }

    @Test
    void itShouldValidateGuestScoreWhenGuestScoreIsNotCorrect() {

        // given
        int guestScore = -2;
        Match match = Match.builder().guestScore(guestScore).build();

        // when
        ValidationResult result = ScoreboardValidator.isGuestScoreValid(guestScore);

        // then
        Assertions.assertEquals(GUEST_TEAM_SCORE_NOT_VALID.getMessage(), result.getMessage());
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
