package expression;

public class ParsingException extends RuntimeException {
    public ParsingException() {}
    public ParsingException(String message) {
        super(message);
    }
    public ParsingException(String message, String s, String t) {
        super(message + "\n" + s + "\n" + t);
    }
}