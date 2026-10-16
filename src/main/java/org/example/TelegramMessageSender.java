
package org.example;

public class TelegramMessageSender implements MessageSender {

    private boolean available = true;

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public boolean sendMessage(String recipient, String title, String body) {
        if (!isAvailable()) {
            System.out.println("Telegram is unavailable");
            return false;
        }

        System.out.println("=== Telegram Message ===");
        System.out.println("To: " + recipient);
        System.out.println("*" + title + "*");
        System.out.println(body);
        System.out.println("Telegram message sent successfully");

        return true;
    }

    @Override
    public boolean isAvailable() {
        return available;
    }

    @Override
    public String getChannelName() {
        return "Telegram";
    }
}
