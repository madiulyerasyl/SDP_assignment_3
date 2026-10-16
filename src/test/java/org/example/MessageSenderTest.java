package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageSenderTest {

    @Test
    void testTelegramSuccessfulDelivery() {

        TelegramMessageSender telegram = new TelegramMessageSender();

        boolean result = telegram.sendMessage(
                "user123",
                "Test",
                "Hello Telegram"
        );

        assertTrue(result);
    }


    @Test
    void testTelegramUnavailable() {

        TelegramMessageSender telegram = new TelegramMessageSender();

        telegram.setAvailable(false);

        boolean result = telegram.sendMessage(
                "user123",
                "Test",
                "Hello Telegram"
        );

        assertFalse(result);
    }


    @Test
    void testSmsCharacterLimit() {

        SmsMessageSender sms = new SmsMessageSender();

        String longMessage = "A".repeat(161);

        boolean result = sms.sendMessage(
                "123456789",
                "Test SMS",
                longMessage
        );

        assertFalse(result);
    }

    @Test
    void testSmsExactly160Characters() {

        SmsMessageSender sms = new SmsMessageSender();

        String message = "A".repeat(160);

        boolean result = sms.sendMessage(
                "123456789",
                "Test SMS",
                message
        );

        assertTrue(result);
    }


    @Test
    void testUrgentNotificationFallback() {

        TelegramMessageSender telegram = new TelegramMessageSender();
        SmsMessageSender sms = new SmsMessageSender();

        telegram.setAvailable(false);

        UrgentNotification urgent = new UrgentNotification(telegram);
        urgent.setFallbackSender(sms);

        java.io.ByteArrayOutputStream output =
                new java.io.ByteArrayOutputStream();

        java.io.PrintStream original = System.out;

        try {
            System.setOut(new java.io.PrintStream(output));

            urgent.send("123456789", "Emergency message");

        } finally {
            System.setOut(original);
        }

        String result = output.toString();

        assertTrue(result.contains("SMS sent successfully"));
        assertTrue(result.contains("Fallback successful!"));
    }

    @Test
    void testRuntimeProviderSwitching() {

        TelegramMessageSender telegram = new TelegramMessageSender();
        EmailMessageSender email = new EmailMessageSender();

        UrgentNotification notification = new UrgentNotification(telegram);

        java.io.ByteArrayOutputStream output =
                new java.io.ByteArrayOutputStream();

        java.io.PrintStream original = System.out;

        try {
            System.setOut(new java.io.PrintStream(output));

            notification.send("user123", "First message");

            notification.setSender(email);

            notification.send("user@example.com", "Second message");

        } finally {
            System.setOut(original);
        }

        String result = output.toString();

        assertTrue(result.contains("Telegram message sent successfully"));
        assertTrue(result.contains("Email sent successfully"));
    }

    @Test
    void testEncryptedNotification() {

        EmailMessageSender email = new EmailMessageSender();

        EncryptedNotification encrypted =
                new EncryptedNotification(email);

        java.io.ByteArrayOutputStream output =
                new java.io.ByteArrayOutputStream();

        java.io.PrintStream original = System.out;

        try {
            System.setOut(new java.io.PrintStream(output));

            encrypted.send("user@example.com", "Hello");

        } finally {
            System.setOut(original);
        }

        String result = output.toString();

        assertTrue(result.contains("SGVsbG8="));
        assertFalse(result.contains("Message: Hello"));
    }

    @Test
    void testScheduledBatchNotification() {

        SmsMessageSender sms = new SmsMessageSender();

        ScheduledBatchNotification batch =
                new ScheduledBatchNotification(sms);

        batch.addRecipient("111111111");
        batch.addRecipient("222222222");
        batch.addRecipient("333333333");

        java.io.ByteArrayOutputStream output =
                new java.io.ByteArrayOutputStream();

        java.io.PrintStream original = System.out;

        try {
            System.setOut(new java.io.PrintStream(output));

            batch.send(null, "Hello everyone!");

        } finally {
            System.setOut(original);
        }

        String result = output.toString();

        assertTrue(result.contains("Total: 3"));
        assertTrue(result.contains("Successful: 3"));
        assertTrue(result.contains("Failed: 0"));
    }

    @Test
    void testPushNotificationSender() {

        PushNotificationSender push = new PushNotificationSender();

        boolean result = push.sendMessage(
                "device123",
                "Test Push",
                "Hello from Push!"
        );

        assertTrue(result);
        assertEquals("Push", push.getChannelName());
    }

    @Test
    void testMarketingNotification() {

        PushNotificationSender push = new PushNotificationSender();

        MarketingNotification marketing =
                new MarketingNotification(push);

        java.io.ByteArrayOutputStream output =
                new java.io.ByteArrayOutputStream();

        java.io.PrintStream original = System.out;

        try {
            System.setOut(new java.io.PrintStream(output));

            marketing.send("device123", "20% discount!");

        } finally {
            System.setOut(original);
        }

        String result = output.toString();

        assertTrue(result.contains("Title: Special Offer"));
        assertTrue(result.contains("Special offer for you! 20% discount!"));
        assertTrue(result.contains("Push notification sent successfully"));
    }

}