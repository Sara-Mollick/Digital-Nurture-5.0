public class Light {
    private final String location;

    public Light(String location) {
        this.location = location;
    }

    public void turnOn() {
        System.out.println("The " + location + " light is now ON.");
    }

    public void turnOff() {
        System.out.println("The " + location + " light is now OFF.");
    }
}
