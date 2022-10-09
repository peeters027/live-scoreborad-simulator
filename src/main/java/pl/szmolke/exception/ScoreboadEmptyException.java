package pl.szmolke.exception;

public class ScoreboadEmptyException extends Exception {

    public ScoreboadEmptyException() {

        super("Currently there's no matches on scoreboard.");
    }
}
