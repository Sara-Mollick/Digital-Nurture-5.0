public class RemoteControl {
    private Command command;

    // Set the command to be executed
    public void setCommand(Command command) {
        this.command = command;
    }

    // Trigger execution
    public void pressButton() {
        if (command == null) {
            System.out.println("No command is assigned to the remote control button!");
            return;
        }
        command.execute();
    }
}
