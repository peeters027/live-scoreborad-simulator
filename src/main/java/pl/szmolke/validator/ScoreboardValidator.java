package pl.szmolke.validator;

import pl.szmolke.database.InMemoryDB;
import pl.szmolke.model.Match;

import java.util.function.Function;

import static pl.szmolke.validator.ScoreboardValidator.ValidationResult;
import static pl.szmolke.validator.ScoreboardValidator.ValidationResult.GUEST_TEAM_ALREADY_IN_PLAY;
import static pl.szmolke.validator.ScoreboardValidator.ValidationResult.GUEST_TEAM_NAME_INVALID;
import static pl.szmolke.validator.ScoreboardValidator.ValidationResult.GUEST_TEAM_SCORE_NOT_VALID;
import static pl.szmolke.validator.ScoreboardValidator.ValidationResult.HOME_TEAM_ALREADY_IN_PLAY;
import static pl.szmolke.validator.ScoreboardValidator.ValidationResult.HOME_TEAM_NAME_INVALID;
import static pl.szmolke.validator.ScoreboardValidator.ValidationResult.HOME_TEAM_SCORE_NOT_VALID;
import static pl.szmolke.validator.ScoreboardValidator.ValidationResult.SUCCESS;

public interface ScoreboardValidator extends Function<Match, ValidationResult> {

    static ScoreboardValidator isHomeTeamNameValid() {
        return match -> !(match.getHomeTeam() == null
                || match.getHomeTeam().isEmpty()) ?
                SUCCESS : HOME_TEAM_NAME_INVALID;
    }

    static ScoreboardValidator isGuestTeamNameValid() {
        return match -> !(match.getGuestTeam() == null
                || match.getGuestTeam().isEmpty()) ?
                SUCCESS : GUEST_TEAM_NAME_INVALID;
    }

    static ScoreboardValidator isHomeTeamAlreadyInPlay() {
        return match -> InMemoryDB.MATCHES.stream().noneMatch(m ->
                m.getHomeTeam().equalsIgnoreCase(match.getHomeTeam())
                        || m.getGuestTeam().equalsIgnoreCase(match.getHomeTeam())) ?
                SUCCESS : HOME_TEAM_ALREADY_IN_PLAY;
    }

    static ScoreboardValidator isGuestTeamAlreadyInPlay() {
        return match -> InMemoryDB.MATCHES.stream().noneMatch(m ->
                m.getHomeTeam().equalsIgnoreCase(match.getGuestTeam())
                        || m.getGuestTeam().equalsIgnoreCase(match.getGuestTeam())) ?
                SUCCESS : GUEST_TEAM_ALREADY_IN_PLAY;
    }

    static ValidationResult isMatchValid(Match match) {
        return isHomeTeamNameValid()
                .and(isGuestTeamNameValid())
                .and(isHomeTeamAlreadyInPlay())
                .and(isGuestTeamAlreadyInPlay())
                .apply(match);
    }

    static ScoreboardValidator isHomeScoreValid() {
        return match -> match.getHomeScore() >= 0 ?
                SUCCESS : HOME_TEAM_SCORE_NOT_VALID;
    }

    static ScoreboardValidator isGuestScoreValid() {
        return match -> match.getGuestScore() >= 0 ?
                SUCCESS : GUEST_TEAM_SCORE_NOT_VALID;
    }

    static ValidationResult isScoreInputValid(Match match) {
        return isHomeScoreValid()
                .and(isGuestScoreValid())
                .apply(match);
    }

    default ScoreboardValidator and(ScoreboardValidator other) {
        return match -> {
            ValidationResult result = this.apply(match);
            return result.equals(SUCCESS) ? other.apply(match) : result;
        };
    }

    enum ValidationResult {
        SUCCESS("Input is valid."),
        HOME_TEAM_NAME_INVALID("Home team name is not valid."),
        GUEST_TEAM_NAME_INVALID("Guest team name is not valid."),
        HOME_TEAM_ALREADY_IN_PLAY("Home team is currently playing a match."),
        GUEST_TEAM_ALREADY_IN_PLAY("Guest team is currently playing a match"),
        HOME_TEAM_SCORE_NOT_VALID("Home score is not valid. Provided score should not be a negative number."),
        GUEST_TEAM_SCORE_NOT_VALID("Guest score is not valid. Provided score should not be a negative number.");


        private final String message;

        ValidationResult(String msg) {
            this.message = msg;
        }

        public String getMessage() {
            return message;
        }
    }
}
