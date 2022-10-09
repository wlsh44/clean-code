package ch14.draft2;


import static ch14.draft2.ErrorCode.INVALID_ARGUMENT_NAME;
import static ch14.draft2.ErrorCode.INVALID_DOUBLE;
import static ch14.draft2.ErrorCode.INVALID_FORMAT;
import static ch14.draft2.ErrorCode.INVALID_INTEGER;
import static ch14.draft2.ErrorCode.MISSING_DOUBLE;
import static ch14.draft2.ErrorCode.MISSING_INTEGER;
import static ch14.draft2.ErrorCode.MISSING_STRING;
import static ch14.draft2.ErrorCode.OK;
import static ch14.draft2.ErrorCode.UNEXPECTED_ARGUMENT;

public class ArgsException extends Exception {
    private ErrorCode errorCode = OK;
    private char errorArgumentId = '\0';
    private String errorParameter = "TILT";

    public ArgsException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.errorParameter = null;
    }

    public ArgsException(ErrorCode errorCode, String errorParameter) {
        this.errorCode = errorCode;
        this.errorParameter = errorParameter;
    }

    public ArgsException(ErrorCode errorCode, char errorArgumentId, String errorParameter) {
        this.errorCode = errorCode;
        this.errorParameter = errorParameter;
        this.errorArgumentId = errorArgumentId;
    }

    @Override
    public String getMessage() {
        switch (errorCode) {
            case UNEXPECTED_ARGUMENT:
                return UNEXPECTED_ARGUMENT.getErrorMessage(errorArgumentId);
            case MISSING_STRING:
                return MISSING_STRING.getErrorMessage(errorArgumentId);
            case MISSING_INTEGER:
                return MISSING_INTEGER.getErrorMessage(errorArgumentId);
            case MISSING_DOUBLE:
                return MISSING_DOUBLE.getErrorMessage(errorArgumentId);
            case INVALID_INTEGER:
                return INVALID_INTEGER.getErrorMessage(errorArgumentId, errorParameter);
            case INVALID_DOUBLE:
                return INVALID_DOUBLE.getErrorMessage(errorArgumentId, errorParameter);
            case INVALID_ARGUMENT_NAME:
                return INVALID_ARGUMENT_NAME.getErrorMessage(errorArgumentId, errorParameter);
            case INVALID_FORMAT:
                return INVALID_FORMAT.getErrorMessage(errorArgumentId, errorParameter);
        }
        return "";
    }

    public void setErrorArgumentId(char errorArgumentId) {
        this.errorArgumentId = errorArgumentId;
    }
}
