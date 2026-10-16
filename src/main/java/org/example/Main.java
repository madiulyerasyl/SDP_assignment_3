
package org.example;

public class Main {

    public static void main(String[] args) {

        TelegramMessageSender telegram = new TelegramMessageSender();
        EmailMessageSender email = new EmailMessageSender();

        UrgentNotification notification = new UrgentNotification(telegram);

        System.out.println("=== First: Telegram ===");
        notification.send("user123", "Hello from Telegram!");

        notification.setSender(email);

        System.out.println("\n=== Second: Email ===");
        notification.send("user@example.com", "Hello from Email!");


        System.out.println("\n=== Third: Telegram Fallback to SMS ===");

        SmsMessageSender sms = new SmsMessageSender();

        telegram.setAvailable(false);

        UrgentNotification urgent =
                new UrgentNotification(telegram);

        urgent.setFallbackSender(sms);

        urgent.send("123456789", "Security alert!");


        System.out.println("\n=== Fourth: Marketing + Push ===");

        PushNotificationSender push = new PushNotificationSender();

        MarketingNotification marketing =
                new MarketingNotification(push);

        marketing.send("device123", "20% discount on smart devices!");


        System.out.println("\n=== Fifth: Encrypted + Email ===");

        EncryptedNotification encrypted =
                new EncryptedNotification(email);

        encrypted.send("user@example.com", "My secret message");


        System.out.println("\n=== Sixth: Batch + SMS ===");

        ScheduledBatchNotification batch =
                new ScheduledBatchNotification(sms);

        batch.addRecipient("111111111");
        batch.addRecipient("222222222");
        batch.addRecipient("333333333");

        batch.send(null, "Your scheduled notification!");

    }
}
