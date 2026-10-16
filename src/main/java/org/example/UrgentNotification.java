
package org.example;

public class UrgentNotification extends Notification {

    private MessageSender fallbackSender;

    public UrgentNotification(MessageSender sender) {
        super(sender);
    }

    public void setFallbackSender(MessageSender fallbackSender) {
        this.fallbackSender = fallbackSender;
    }

    @Override
    public void send(String recipient, String message) {
        String title = "Urgent Notification";
        String urgentMessage = "[URGENT] " + message;

        if (sender.isAvailable()) {
            boolean success = sender.sendMessage(
                    recipient, title, urgentMessage
            );

            if (success) {
                return;
            }
        }

        System.out.println("Primary sender failed.");

        if (fallbackSender != null && fallbackSender.isAvailable()) {
            boolean success = fallbackSender.sendMessage(
                    recipient, title, urgentMessage
            );

            if (success) {
                System.out.println("Fallback successful!");
                return;
            }
        }

        System.out.println("Notification failed.");
    }
}
