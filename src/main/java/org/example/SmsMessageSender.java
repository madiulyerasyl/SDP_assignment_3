
package org.example;

public class SmsMessageSender implements MessageSender {

    private boolean available = true;

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public boolean sendMessage(String recipient, String title, String body) {
        if (!isAvailable()) {
            System.out.println("SMS is unavailable");
            return false;
        }

        if (body == null || body.length() > 160) {
            System.out.println("SMS failed: message must be 160 characters or less");
            return false;
        }

        System.out.println("=== SMS Message ===");
        System.out.println("To: " + recipient);
        System.out.println("Message: " + body);
        System.out.println("SMS sent successfully");

        return true;
    }

    @Override
    public boolean isAvailable() {
        return available;
    }

    @Override
    public String getChannelName() {
        return "SMS";
    }
}
