public class StateException extends Exception
{
    public enum ExceptionType
    {
        NO_SUCH_CLASS, NO_SUCH_FILED,NO_SUCH_FILE,INVALID_DELIMITER,NO_SUCH_HEADER,NO_SUCH_FILE_TYPE,SOME_OTHER_FILE_ERROR
    }

    public ExceptionType type;

    public StateException(ExceptionType type, String message)
    {
        super(message);
        this.type = type;
    }

    public StateException(ExceptionType type, Throwable cause)
    {
        super(cause);
        this.type = type;
    }

    public StateException(ExceptionType type, String message, Throwable cause)
    {
        super(message, cause);
        this.type = type;
    }
}
