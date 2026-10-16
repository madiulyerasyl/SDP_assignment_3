
package org.example;

public class KafkaEventSender implements MessageSender {

    private boolean available = true;

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public boolean sendMessage(String recipient, String title, String body) {
        if (!isAvailable()) {
            System.out.println("Kafka is unavailable");
            return false;
        }

        System.out.println("=== Kafka Event ===");
        System.out.println("Topic: notifications");
        System.out.println("Recipient: " + recipient);
        System.out.println("Event title: " + title);
        System.out.println("Event body: " + body);
        System.out.println("Event published successfully");

        return true;
    }

    @Override
    public boolean isAvailable() {
        return available;
    }

    @Override
    public String getChannelName() {
        return "Kafka";
    }
}
