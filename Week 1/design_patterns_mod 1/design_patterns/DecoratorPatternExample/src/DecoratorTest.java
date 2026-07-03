public class DecoratorTest {
    public static void main(String[] args) {
        System.out.println("=== Testing Decorator Pattern ===");

        // 1. Basic Notifier (Email only)
        Notifier emailNotifier = new EmailNotifier();
        System.out.println("--- Scenario 1: Email only ---");
        emailNotifier.send("System Update: Server #4 is running smoothly.");

        System.out.println();

        // 2. Decorate with SMS (Email + SMS)
        Notifier emailAndSMS = new SMSNotifierDecorator(new EmailNotifier());
        System.out.println("--- Scenario 2: Email + SMS ---");
        emailAndSMS.send("Security Alert: Unrecognized login attempt detected.");

        System.out.println();

        // 3. Decorate with both SMS and Slack (Email + SMS + Slack)
        Notifier emailSMSAndSlack = new SlackNotifierDecorator(
                new SMSNotifierDecorator(
                        new EmailNotifier()
                )
        );
        System.out.println("--- Scenario 3: Email + SMS + Slack ---");
        emailSMSAndSlack.send("Critical Alert: Production database is unreachable!");
    }
}
