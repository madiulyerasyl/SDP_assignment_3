
package org.example;

import java.util.ArrayList;
import java.util.List;

public class ScheduledBatchNotification extends Notification {

    private List<String> recipients = new ArrayList<>();

    public ScheduledBatchNotification(MessageSender sender) {
        super(sender);
    }

    public void addRecipient(String recipient) {
        recipients.add(recipient);
    }

    @Override
    public void send(String recipient, String message) {

        if (recipient != null && !recipient.isEmpty()) {
            recipients.add(recipient);
        }

        int total = recipients.size();
        int successful = 0;
        int failed = 0;

        System.out.println("=== Batch Notification ===");

        for (String user : recipients) {
            boolean success = false;

            if (sender.isAvailable()) {
                success = sender.sendMessage(
                        user, "Batch Notification", message
                );
            }

            if (success) {
                successful++;
            } else {
                failed++;
            }
        }

        System.out.println("=== Batch Summary ===");
        System.out.println("Total: " + total);
        System.out.println("Successful: " + successful);
        System.out.println("Failed: " + failed);
    }
}
