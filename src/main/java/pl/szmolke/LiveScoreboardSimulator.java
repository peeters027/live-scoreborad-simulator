package pl.szmolke;

import pl.szmolke.exception.IndexFormatException;
import pl.szmolke.exception.TeamNameFormatException;
import pl.szmolke.service.ScoreboardService;
import pl.szmolke.validator.ValidationResult;

import java.util.Scanner;

import static pl.szmolke.validator.InputValidator.validateIndexFromScoreboard;
import static pl.szmolke.validator.InputValidator.validateInputAsNumber;
import static pl.szmolke.validator.ValidationResult.SUCCESS;

public class LiveScoreboardSimulator {
    public static void main(String[] args) {

        ScoreboardService scoreboardService = new ScoreboardService();

        while (true) {
            Scanner input = new Scanner(System.in);
            System.out.println("\nSimulator Main Menu");
            System.out.print("1.) Start a game \n");
            System.out.print("2.) Finish a game.\n");
            System.out.print("3.) Update score.\n");
            System.out.print("4.) Get summary of games in total score.\n");
            System.out.print("5.) Exit\n");
            System.out.print("\nEnter Your Menu Choice: ");

            ValidationResult inputValidationResult = validateInputAsNumber().apply(input);
            int choice;

            if (inputValidationResult != SUCCESS) {
                System.out.println(inputValidationResult.getMessage());
                choice = -1;
            } else {
                choice = Integer.parseInt(input.nextLine());
            }

            switch (choice) {
                case 1:
                    System.out.println("\nStarting a game... ");
                    System.out.println("Provide a name of home team");
                    String homeTeam = input.nextLine();
                    System.out.println("Provide a name of guest team");
                    String guestTeam = input.nextLine();
                    try {
                        scoreboardService.startMatch(homeTeam, guestTeam);
                    } catch (TeamNameFormatException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 2:
                    System.out.println("\nFinishing a game...");
                    System.out.println("Which match would you like to remove? ");
                    System.out.println("Please provide the index of the match.");
                    scoreboardService.getSummaryOfGames();
                    try {
                        scoreboardService.removeMatch(getIndexFromInput(input));
                        System.out.println("Match has been removed.");
                    } catch (IndexFormatException e) {
                        System.out.println(e.getMessage());
                        break;
                    }
                    break;
                case 3:
                    System.out.println("\nUpdating a game...");
                    break;
                case 4:
                    System.out.println("\nSummary of games...");
                    scoreboardService.getSummaryOfGames();
                    break;
                case 5:
                    System.out.println("\nExiting Program...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("This is not a valid menu option! Please select again.");
                    break;
            }
        }
    }

    private static Integer getIndexFromInput(Scanner input) throws IndexFormatException {
        ValidationResult isInputNumber = validateInputAsNumber().apply(input);
        if (isInputNumber != SUCCESS) {
            input.nextLine();
            throw new IndexFormatException(isInputNumber.getMessage());
        }
        int index = input.nextInt();
        ValidationResult isIndexInScoreboard = validateIndexFromScoreboard(index);
        if (isIndexInScoreboard != SUCCESS) {
            throw new IndexFormatException(isIndexInScoreboard.getMessage());
        }
        return index;
    }
}
