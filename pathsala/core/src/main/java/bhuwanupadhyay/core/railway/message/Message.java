package bhuwanupadhyay.core.railway.message;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.Objects;

public final class Message implements Serializable {
    private static final long serialVersionUID = 8128659250371079506L;
    private static final int POSITION_OF_CALLER_IN_CALL_STACK = 7;

    private MessageLevel level;
    private int code;
    private String source;
    private int index;
    private String text;
    private String details;

    private Message() {
        level = MessageLevel.ERROR;
        code = 1;
        source = getSourceFromCallStack();
        index = 0;
        text = "No text";
        details = "No details";
    }

    public static MessageBuilder create() {
        return new MessageBuilder();
    }

    public static MessageBuilder createError() {
        return new MessageBuilder()
                .withLevel(MessageLevel.ERROR);
    }

    public static Message withError(final String text) {
        return new MessageBuilder()
                .withLevel(MessageLevel.ERROR)
                .withText(text)
                .build();
    }

    private String getSourceFromCallStack() {
        final StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        if (stack.length >= POSITION_OF_CALLER_IN_CALL_STACK + 1) {
            return stack[POSITION_OF_CALLER_IN_CALL_STACK].getMethodName();
        }
        return "No source";
    }

    public int getCode() {
        return code;
    }

    public MessageLevel getLevel() {
        return level;
    }

    public int getIndex() {
        return index;
    }

    public String getSource() {
        return source;
    }

    public String getText() {
        return text;
    }

    public String getDetails() {
        return details;
    }

    public boolean hasCode(final int code) {
        return getCode() == code;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCode(),
                getLevel(),
                getIndex(),
                getSource(),
                getText(),
                getDetails());
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Message other = (Message) obj;
        return Objects.equals(getCode(), other.getCode())
                && Objects.equals(getLevel(), other.getLevel())
                && Objects.equals(getIndex(), other.getIndex())
                && Objects.equals(getSource(), other.getSource())
                && Objects.equals(getText(), other.getText())
                && Objects.equals(getDetails(), other.getDetails());
    }

    @Override
    public String toString() {
        return String.format("%s (%s, %s, %s): \"%s\" (details: \"%s\")",
                getLevel(), getCode(), getSource(), getIndex(), getText(), getDetails());
    }

    public static class MessageBuilder {
        private final Message message;

        private MessageBuilder() {
            message = new Message();
        }

        public Message build() {
            return message;
        }

        public MessageBuilder withCode(final int code) {
            message.code = code;
            return this;
        }

        public MessageBuilder withLevel(final MessageLevel level) {
            message.level = level;
            return this;
        }

        public MessageBuilder withIndex(final int index) {
            message.index = index;
            return this;
        }

        public MessageBuilder withText(final String text) {
            message.text = text;
            return this;
        }

        public MessageBuilder withSource(final String source) {
            message.source = source;
            return this;
        }

        public MessageBuilder withDetails(final String details) {
            message.details = details;
            return this;
        }

        public MessageBuilder withDetails(final Throwable throwable) {
            final StringWriter stringWriter = new StringWriter();
            final PrintWriter printWriter = new PrintWriter(stringWriter);
            throwable.printStackTrace(printWriter);
            message.details = stringWriter.toString();
            return this;
        }
    }
}