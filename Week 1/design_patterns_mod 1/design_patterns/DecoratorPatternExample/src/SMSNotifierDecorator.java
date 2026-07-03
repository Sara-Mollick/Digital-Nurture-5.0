public class SMSNotifierDecorator extends NotifierDecorator {
    public SMSNotifierDecorator(Notifier notifier) {
        super(notifier);
    }

    @Override
    public void send(String message) {
        super.send(message); // sends the wrapped notifier's messages
        sendSMS(message);     // then adds SMS functionality
    }

    private void sendSMS(String message) {
        System.out.println("Sending SMS Notification: " + message);
    }
}
