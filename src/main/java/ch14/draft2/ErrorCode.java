package ch14.draft2;

public enum ErrorCode {
    OK("TILT: Should not get here"),
    MISSING_STRING("Could not find string parameter for -%c"),
    MISSING_INTEGER("Could not find integer parameter for -%c"),
    MISSING_DOUBLE("Could not find double parameter for -%c"),
    INVALID_INTEGER("Argument -%c expects an integer but was %s"),
    INVALID_FORMAT("Argument: %c has invalid format: %s"),
    INVALID_ARGUMENT_NAME("Bad character: %c in Args format: %s"),
    UNEXPECTED_ARGUMENT("Argument(s) -%c unexpected"),
    INVALID_DOUBLE("Argument -%c expects an double but was %s");

    private String errorMessage;

    ErrorCode(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage(Character c) {
        return String.format(errorMessage, c);
    }

    public String getErrorMessage(Character c, String s) {
        return String.format(errorMessage, c, s);
    }
}
