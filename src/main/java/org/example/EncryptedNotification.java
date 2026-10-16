
package org.example;

import java.util.Base64;
import java.nio.charset.StandardCharsets;

public class EncryptedNotification extends Notification {

    public EncryptedNotification(MessageSender sender) {
        super(sender);
    }

    @Override
    public void send(String recipient, String message) {

        if (!sender.isAvailable()) {
            System.out.println("Sender is unavailable");
            return;
        }

        String encodedMessage = Base64.getEncoder()
                .encodeToString(message.getBytes(StandardCharsets.UTF_8));

        String title = "Encrypted Notification";

        boolean success = sender.sendMessage(
                recipient, title, encodedMessage
        );

        if (success) {
            System.out.println("Encrypted notification sent successfully");
        } else {
            System.out.println("Encrypted notification failed");
        }
    }
}
