package pl.szmolke.validator;

import pl.szmolke.database.InMemoryDB;
import pl.szmolke.model.Match;

import java.util.function.Function;
import java.util.regex.Pattern;

import static pl.szmolke.validator.ValidationResult.GUEST_TEAM_ALREADY_IN_PLAY;
import static pl.szmolke.validator.ValidationResult.GUEST_TEAM_NAME_INVALID;
import static pl.szmolke.validator.ValidationResult.GUEST_TEAM_SCORE_INVALID;
import static pl.szmolke.validator.ValidationResult.HOME_TEAM_ALREADY_IN_PLAY;
import static pl.szmolke.validator.ValidationResult.HOME_TEAM_NAME_INVALID;
import static pl.szmolke.validator.ValidationResult.HOME_TEAM_SCORE_INVALID;
import static pl.szmolke.validator.ValidationResult.SUCCESS;


public interface ScoreboardValidator extends Function<Match, ValidationResult> {

    Pattern pattern = Pattern.compile("\\d");

    static ScoreboardValidator isHomeTeamNameValid() {
        return match -> !(match.getHomeTeam() == null
                || match.getHomeTeam().isEmpty()
                || pattern.matcher(match.getHomeTeam()).find()) ?
                SUCCESS : HOME_TEAM_NAME_INVALID;
    }

    static ScoreboardValidator isGuestTeamNameValid() {
        return match -> !(match.getGuestTeam() == null
                || match.getGuestTeam().isEmpty()
                || pattern.matcher(match.getGuestTeam()).find()) ?
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

    static ValidationResult isHomeScoreValid(int homeScore) {
        return homeScore >= 0 ?
                SUCCESS : HOME_TEAM_SCORE_INVALID;
    }

    static ValidationResult isGuestScoreValid(int guestScore) {
        return guestScore >= 0 ?
                SUCCESS : GUEST_TEAM_SCORE_INVALID;
    }

    static ValidationResult isScoreValid(int homeScore, int guestScore) {
        return isHomeScoreValid(homeScore) == SUCCESS ? isGuestScoreValid(guestScore) : HOME_TEAM_SCORE_INVALID;

    }

    default ScoreboardValidator and(ScoreboardValidator other) {
        return match -> {
            ValidationResult result = this.apply(match);
            return result.equals(SUCCESS) ? other.apply(match) : result;
        };
    }
}
