public class SlackNotifierDecorator extends NotifierDecorator {
    public SlackNotifierDecorator(Notifier notifier) {
        super(notifier);
    }

    @Override
    public void send(String message) {
        super.send(message); // sends the wrapped notifier's messages
        sendSlackMessage(message); // then adds Slack functionality
    }

    private void sendSlackMessage(String message) {
        System.out.println("Sending Slack Channel Notification: " + message);
    }
}
