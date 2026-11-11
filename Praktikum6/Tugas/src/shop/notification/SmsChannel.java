package shop.notification;

public class SmsChannel extends AbstractChannel {
    private static final String ELLIPSIS = "...";

    private final int maxLength;

    public SmsChannel(int maxLength) {
        if (maxLength < ELLIPSIS.length()) {
            throw new IllegalArgumentException("maxLength must be at least " + ELLIPSIS.length());
        }
        this.maxLength = maxLength;
    }

    @Override
    public String channelName() {
        return "SMS";
    }

    @Override
    protected String resolveRecipient(CustomerContact contact) {
        return contact.getPhone().startsWith("08") ? contact.getPhone() : null;
    }

    @Override
    protected String prepare(String message) {
        if (message.length() <= maxLength) {
            return message;
        }
        return message.substring(0, maxLength - ELLIPSIS.length()) + ELLIPSIS;
    }

    @Override
    protected boolean deliver(String recipient, String message) {
        System.out.println("[SMS to " + recipient + "] " + message);
        return true;
    }
}