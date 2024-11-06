package store.exception;

/**
 * 초기 상태로 되돌리는 예외
 */

public class ResetStateException extends RuntimeException {
    public ResetStateException(String message) {
        super(message);
    }
}
