package pl.szmolke.validator;

import pl.szmolke.database.InMemoryDB;

import java.util.Scanner;
import java.util.function.Function;

import static pl.szmolke.validator.ValidationResult.INDEX_FROM_SCOREBOARD_INVALID;
import static pl.szmolke.validator.ValidationResult.SUCCESS;

public interface InputValidator extends Function<Scanner, ValidationResult> {

    static InputValidator validateIndexFromScoreboard() {
        return input -> input.hasNextInt() ? SUCCESS : INDEX_FROM_SCOREBOARD_INVALID;
    }

    static ValidationResult validateIndexFromScoreboard(int index) {
        return index <= InMemoryDB.MATCHES.size() && index > 0 ? SUCCESS : INDEX_FROM_SCOREBOARD_INVALID;
    }
}
