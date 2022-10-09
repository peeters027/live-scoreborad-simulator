package pl.szmolke;

import pl.szmolke.exception.IndexFormatException;
import pl.szmolke.exception.TeamNameFormatException;
import pl.szmolke.service.InputService;
import pl.szmolke.service.ScoreboardService;

import java.util.Scanner;

public class LiveScoreboardSimulator {
    public static void main(String[] args) {

        ScoreboardService scoreboardService = new ScoreboardService();
        InputService inputService = new InputService();

        while (true) {
            Scanner input = new Scanner(System.in);
            System.out.println("\nSimulator Main Menu");
            System.out.print("1.) Start a game \n");
            System.out.print("2.) Finish a game.\n");
            System.out.print("3.) Update score.\n");
            System.out.print("4.) Get summary of games in total score.\n");
            System.out.print("5.) Exit\n");
            System.out.print("\nEnter Your Menu Choice: ");

            int choice = inputService.getMenuOption(input);

            switch (choice) {
                case 1:
                    try {
                        System.out.println("\nStarting a game... ");
                        System.out.println("Provide a name of home team");
                        input.nextLine();
                        String homeTeam = input.nextLine();
                        System.out.println("Provide a name of guest team");
                        String guestTeam = input.nextLine();
                        scoreboardService.startMatch(homeTeam, guestTeam);
                    } catch (TeamNameFormatException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 2:
                    try {
                        System.out.println("\nFinishing a game...");
                        System.out.println("Which match would you like to remove? ");
                        System.out.println("Please provide the index of the match.");
                        scoreboardService.getSummaryOfGames();
                        int index = inputService.getMatchIndexFromInput(input);
                        scoreboardService.removeMatch(index);
                        System.out.println("Match has been removed.");
                    } catch (IndexFormatException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 3:
                    try {
                        System.out.println("\nUpdating a game...");
                        System.out.println("Which match would you like to update? ");
                        System.out.println("Please provide the index of the match.");
                        scoreboardService.getSummaryOfGames();
                        int index = inputService.getMatchIndexFromInput(input);
                        System.out.println("Please provide the home team score.");
                        Integer homeScore = inputService.getNumberFromInput(input);
                        System.out.println("Please provide the guest team score.");
                        Integer guestScore = inputService.getNumberFromInput(input);
                        System.out.println("Please provide the guest team score.");
                        scoreboardService.updateMatch(index, homeScore, guestScore);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 4:
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
}
