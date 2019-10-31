package xin.luowei.cloud.lock.exception;

/**
 * DuplicateSubmmitException
 */
public class DuplicateSubmmitException extends RuntimeException{
    private static final long serialVersionUID = 5833983583432482028L;

    public  DuplicateSubmmitException() {
        super();
    }

    public  DuplicateSubmmitException(String message) {
        super(message);
    }
}