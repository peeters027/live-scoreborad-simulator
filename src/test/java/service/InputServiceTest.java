package service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.szmolke.exception.IndexFormatException;
import pl.szmolke.model.Match;
import pl.szmolke.service.InputService;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static pl.szmolke.database.InMemoryDB.MATCHES;
import static pl.szmolke.validator.ValidationResult.INDEX_FROM_SCOREBOARD_INVALID;
import static pl.szmolke.validator.ValidationResult.INPUT_IS_NOT_NUMBER;

public class InputServiceTest {

    InputService inputService;

    @BeforeEach
    void setUp() {
        inputService = new InputService();
        initInMemoryDb();
    }

    @Test
    void itShouldGetNumberFromInputWhenInputIsNumber() throws IndexFormatException {

        // given
        ByteArrayInputStream in = new ByteArrayInputStream("1".getBytes());
        Scanner input = new Scanner(in);

        // when
        Integer index = inputService.getNumberFromInput(input);

        // then
        assertEquals(1, index);
    }

    @Test
    void itShouldThrowExceptionWhenGetNumberFromInputWithStringInput() {

        // given
        ByteArrayInputStream in = new ByteArrayInputStream("text".getBytes());
        Scanner input = new Scanner(in);

        // when
        IndexFormatException thrown = assertThrows(IndexFormatException.class, () -> inputService.getNumberFromInput(input));

        // then
        assertEquals(INPUT_IS_NOT_NUMBER.getMessage(), thrown.getMessage());
    }

    @Test
    void itShouldGetMatchIndexFromInputWhenIndexIsCorrect() throws IndexFormatException {

        // given
        ByteArrayInputStream in = new ByteArrayInputStream("1".getBytes());
        Scanner input = new Scanner(in);

        // when
        Integer index = inputService.getMatchIndexFromInput(input);

        // then
        assertEquals(1, index);
    }

    @Test
    void itShouldThrowExceptionWhenGetMatchIndexFromInputWithIndexIsBiggerThanMatchesSize() {

        // given
        ByteArrayInputStream in = new ByteArrayInputStream("3".getBytes());
        Scanner input = new Scanner(in);

        // when
        IndexFormatException thrown = assertThrows(IndexFormatException.class, () -> inputService.getMatchIndexFromInput(input));

        // then
        assertEquals(INDEX_FROM_SCOREBOARD_INVALID.getMessage(), thrown.getMessage());
    }

    @Test
    void itShouldThrowExceptionWhenGetMatchIndexFromInputWithNegativeIndex() {

        // given
        ByteArrayInputStream in = new ByteArrayInputStream("-1".getBytes());
        Scanner input = new Scanner(in);

        // when
        IndexFormatException thrown = assertThrows(IndexFormatException.class, () -> inputService.getMatchIndexFromInput(input));

        // then
        assertEquals(INDEX_FROM_SCOREBOARD_INVALID.getMessage(), thrown.getMessage());
    }

    @Test
    void itShouldGetMenuOptionWhenOptionIsPositiveNumber() {

        // given
        ByteArrayInputStream in = new ByteArrayInputStream("1".getBytes());
        Scanner input = new Scanner(in);

        // when
        Integer index = inputService.getMenuOption(input);

        // then
        assertEquals(1, index);
    }

    @Test
    void itShouldReturnMinusOneWhenGetMenuOptionWithStringValue() {

        // given
        ByteArrayInputStream in = new ByteArrayInputStream("text".getBytes());
        Scanner input = new Scanner(in);

        // when
        Integer menuOption = inputService.getMenuOption(input);

        // then
        assertEquals(-1, menuOption);
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
