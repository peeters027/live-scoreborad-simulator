package validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.szmolke.database.InMemoryDB;
import pl.szmolke.model.Match;
import pl.szmolke.validator.InputValidator;
import pl.szmolke.validator.ValidationResult;

import java.util.ArrayList;
import java.util.Arrays;

public class InputValidatorTest {

    @BeforeEach
    void setUp() {
        initInMemoryDb();
    }

    @Test
    void validateProvidedIndexFromScoreboard() {

        // given
        int index = 1;

        // when
        ValidationResult result = InputValidator.validateIndexFromScoreboard(index);

        // then
        Assertions.assertEquals(ValidationResult.SUCCESS.getMessage(), result.getMessage());
    }

    private void initInMemoryDb() {
        InMemoryDB.MATCHES = new ArrayList<>(Arrays.asList(
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
