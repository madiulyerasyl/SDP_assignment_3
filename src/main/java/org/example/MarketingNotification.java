
package org.example;

public class MarketingNotification extends Notification {

    public MarketingNotification(MessageSender sender) {
        super(sender);
    }

    @Override
    public void send(String recipient, String message) {

        if (!sender.isAvailable()) {
            System.out.println("Marketing notification failed: sender unavailable");
            return;
        }

        String title = "Special Offer";
        String marketingMessage = "Special offer for you! " + message;

        boolean success = sender.sendMessage(
                recipient, title, marketingMessage
        );

        if (success) {
            System.out.println("Marketing notification sent successfully");
        } else {
            System.out.println("Marketing notification failed");
        }
    }
}
