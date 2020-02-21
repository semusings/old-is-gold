package io.github.bhuwanupadhyay.railway.message;

public class MessageRuntimeException extends RuntimeException {

    public MessageRuntimeException(Message message) {
        super(message.getText());
    }
}
