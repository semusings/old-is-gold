package bhuwanupadhyay.core.railway;

public class FailedResultHasNoValueException extends RuntimeException {

    private final Object error;

    public FailedResultHasNoValueException(final Object error) {
        super();
        this.error = error;
    }

    public Object getError() {
        return error;
    }

    @Override
    public String toString() {
        return "FailedResultHasNoValueException [error=" + getError() + "]";
    }
}