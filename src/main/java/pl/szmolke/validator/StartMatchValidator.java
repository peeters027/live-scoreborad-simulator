package pl.szmolke.validator;

import pl.szmolke.database.InMemoryDB;
import pl.szmolke.model.Match;

import java.util.function.Function;

import static pl.szmolke.validator.StartMatchValidator.ValidationResult;
import static pl.szmolke.validator.StartMatchValidator.ValidationResult.GUEST_TEAM_ALREADY_IN_PLAY;
import static pl.szmolke.validator.StartMatchValidator.ValidationResult.GUEST_TEAM_NAME_INVALID;
import static pl.szmolke.validator.StartMatchValidator.ValidationResult.HOME_TEAM_ALREADY_IN_PLAY;
import static pl.szmolke.validator.StartMatchValidator.ValidationResult.HOME_TEAM_NAME_INVALID;
import static pl.szmolke.validator.StartMatchValidator.ValidationResult.SUCCESS;

public interface StartMatchValidator extends Function<Match, ValidationResult> {

    static StartMatchValidator isHomeTeamNameValid() {
        return match -> !(match.getHomeTeam() == null
                || match.getHomeTeam().isEmpty()) ?
                SUCCESS : HOME_TEAM_NAME_INVALID;
    }

    static StartMatchValidator isGuestTeamNameValid() {
        return match -> !(match.getGuestTeam() == null
                || match.getGuestTeam().isEmpty()) ?
                SUCCESS : GUEST_TEAM_NAME_INVALID;
    }

    static StartMatchValidator isHomeTeamAlreadyInPlay() {
        return match -> InMemoryDB.MATCHES.stream().noneMatch(m -> m.getHomeTeam().equals(match.getHomeTeam())
                || m.getGuestTeam().equals(match.getHomeTeam())) ?
                SUCCESS : HOME_TEAM_ALREADY_IN_PLAY;
    }

    static StartMatchValidator isGuestTeamAlreadyInPlay() {
        return match -> InMemoryDB.MATCHES.stream().noneMatch(m -> m.getHomeTeam().equals(match.getGuestTeam())
                || m.getGuestTeam().equals(match.getGuestTeam())) ?
                SUCCESS : GUEST_TEAM_ALREADY_IN_PLAY;
    }

    default StartMatchValidator and(StartMatchValidator other) {
        return match -> {
            ValidationResult result = this.apply(match);
            return result.equals(SUCCESS) ? other.apply(match) : result;
        };
    }

    enum ValidationResult {
        SUCCESS,
        HOME_TEAM_NAME_INVALID,
        GUEST_TEAM_NAME_INVALID,
        HOME_TEAM_ALREADY_IN_PLAY,
        GUEST_TEAM_ALREADY_IN_PLAY
    }
}
