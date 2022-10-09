package pl.szmolke.validator;

import pl.szmolke.database.InMemoryDB;

import java.util.Scanner;
import java.util.function.Function;

import static pl.szmolke.validator.ValidationResult.INDEX_FROM_SCOREBOARD_INVALID;
import static pl.szmolke.validator.ValidationResult.INPUT_IS_NOT_NUMBER;
import static pl.szmolke.validator.ValidationResult.SUCCESS;

public interface InputValidator extends Function<Scanner, ValidationResult> {

    static InputValidator validateInputAsNumber() {
        return input -> input.hasNextInt() ?
                SUCCESS : INPUT_IS_NOT_NUMBER;
    }

    static ValidationResult validateIndexFromScoreboard(int index) {
        return index <= InMemoryDB.MATCHES.size() && index > 0 ?
                SUCCESS : INDEX_FROM_SCOREBOARD_INVALID;
    }
}
