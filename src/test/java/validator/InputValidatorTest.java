package validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.szmolke.validator.InputValidator;
import pl.szmolke.validator.ValidationResult;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

import static pl.szmolke.validator.ValidationResult.INDEX_FROM_SCOREBOARD_INVALID;
import static pl.szmolke.validator.ValidationResult.INPUT_IS_NOT_NUMBER;
import static pl.szmolke.validator.ValidationResult.SUCCESS;
import static utils.TestHelper.initInMemoryDb;

public class InputValidatorTest {

    @BeforeEach
    void setUp() {

        initInMemoryDb();
    }

    @Test
    void validateIndexFromScoreboardWhenIndexIsCorrect() {

        // given
        int index = 1;

        // when
        ValidationResult result = InputValidator.validateIndexFromScoreboard(index);

        // then
        Assertions.assertEquals(SUCCESS.getMessage(), result.getMessage());
    }

    @Test
    void validateIndexFromScoreboardWhenIndexIsNegativeNumber() {

        // given
        int index = -1;

        // when
        ValidationResult result = InputValidator.validateIndexFromScoreboard(index);

        // then
        Assertions.assertEquals(INDEX_FROM_SCOREBOARD_INVALID.getMessage(), result.getMessage());
    }

    @Test
    void validateIndexFromScoreboardWhenIndexIsBiggerThanMatchesSize() {

        // given
        int index = 100;

        // when
        ValidationResult result = InputValidator.validateIndexFromScoreboard(index);

        // then
        Assertions.assertEquals(INDEX_FROM_SCOREBOARD_INVALID.getMessage(), result.getMessage());
    }

    @Test
    void validateIndexFromScoreboardWhenProvidedIndexIsInteger() {
        // given
        ByteArrayInputStream in = new ByteArrayInputStream("1".getBytes());
        Scanner input = new Scanner(in);

        // when
        ValidationResult result = InputValidator.validateInputAsNumber().apply(input);

        // then
        Assertions.assertEquals(SUCCESS.getMessage(), result.getMessage());
    }

    @Test
    void validateIndexFromScoreboardWhenProvidedIndexIsString() {
        // given
        ByteArrayInputStream in = new ByteArrayInputStream("text".getBytes());
        Scanner input = new Scanner(in);

        // when
        ValidationResult result = InputValidator.validateInputAsNumber().apply(input);

        // then
        Assertions.assertEquals(INPUT_IS_NOT_NUMBER.getMessage(), result.getMessage());
    }
}
