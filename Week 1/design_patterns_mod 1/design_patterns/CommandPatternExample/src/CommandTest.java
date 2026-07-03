public class CommandTest {
    public static void main(String[] args) {
        System.out.println("=== Testing Command Pattern ===");

        // Create Receiver
        Light livingRoomLight = new Light("Living Room");

        // Create Commands
        Command lightOn = new LightOnCommand(livingRoomLight);
        Command lightOff = new LightOffCommand(livingRoomLight);

        // Create Invoker
        RemoteControl remote = new RemoteControl();

        // 1. Turn on the light
        remote.setCommand(lightOn);
        System.out.println("Pressing button to turn ON light:");
        remote.pressButton();

        System.out.println();

        // 2. Turn off the light
        remote.setCommand(lightOff);
        System.out.println("Pressing button to turn OFF light:");
        remote.pressButton();
    }
}
