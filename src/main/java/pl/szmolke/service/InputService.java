package pl.szmolke.service;

import pl.szmolke.exception.IndexFormatException;
import pl.szmolke.validator.ValidationResult;

import java.util.Scanner;

import static pl.szmolke.validator.InputValidator.validateIndexFromScoreboard;
import static pl.szmolke.validator.InputValidator.validateInputAsNumber;
import static pl.szmolke.validator.ValidationResult.SUCCESS;

public class InputService {
    
    public Integer getMenuOption(Scanner input) {
        try {
            return getNumberFromInput(input);
        } catch (IndexFormatException e) {
            System.out.println(e.getMessage());
            return -1;
        }
    }

    public Integer getMatchIndexFromInput(Scanner input) throws IndexFormatException {
        int index = getNumberFromInput(input);
        ValidationResult isIndexInScoreboard = validateIndexFromScoreboard(index);
        if (isIndexInScoreboard != SUCCESS) {
            throw new IndexFormatException(isIndexInScoreboard.getMessage());
        }
        return index;
    }

    public Integer getNumberFromInput(Scanner input) throws IndexFormatException {
        ValidationResult isInputNumber = validateInputAsNumber().apply(input);
        if (isInputNumber != SUCCESS) {
            input.nextLine();
            throw new IndexFormatException(isInputNumber.getMessage());
        }
        return input.nextInt();
    }
}
