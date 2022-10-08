package pl.szmolke.validator;

public enum ValidationResult {
    SUCCESS("Input is valid."),
    HOME_TEAM_NAME_INVALID("Home team name is not valid."),
    GUEST_TEAM_NAME_INVALID("Guest team name is not valid."),
    HOME_TEAM_ALREADY_IN_PLAY("Home team is currently playing a match."),
    GUEST_TEAM_ALREADY_IN_PLAY("Guest team is currently playing a match"),
    HOME_TEAM_SCORE_INVALID("Home score is not valid. Provided score should not be a negative number."),
    GUEST_TEAM_SCORE_INVALID("Guest score is not valid. Provided score should not be a negative number."),
    INDEX_FROM_SCOREBOARD_INVALID("Provided index is not valid");


    private final String message;

    ValidationResult(String msg) {
        this.message = msg;
    }

    public String getMessage() {
        return message;
    }
}
