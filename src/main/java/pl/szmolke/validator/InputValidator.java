package pl.szmolke.validator;

import pl.szmolke.database.InMemoryDB;

import static pl.szmolke.validator.ValidationResult.INDEX_FROM_SCOREBOARD_INVALID;
import static pl.szmolke.validator.ValidationResult.SUCCESS;

public class InputValidator {
    public static ValidationResult validateIndexFromScoreboard(int index) {

        return index <= InMemoryDB.MATCHES.size() && index > 0 ? SUCCESS : INDEX_FROM_SCOREBOARD_INVALID;
    }
}
