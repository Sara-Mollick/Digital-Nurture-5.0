public class Computer {
    // Attributes
    private final String CPU;
    private final String RAM;
    private final String storage;
    
    // Optional attributes
    private final String GPU;
    private final boolean hasWiFi;
    private final boolean hasBluetooth;

    // Private constructor taking Builder as parameter
    private Computer(Builder builder) {
        this.CPU = builder.CPU;
        this.RAM = builder.RAM;
        this.storage = builder.storage;
        this.GPU = builder.GPU;
        this.hasWiFi = builder.hasWiFi;
        this.hasBluetooth = builder.hasBluetooth;
    }

    // Getters
    public String getCPU() {
        return CPU;
    }

    public String getRAM() {
        return RAM;
    }

    public String getStorage() {
        return storage;
    }

    public String getGPU() {
        return GPU;
    }

    public boolean hasWiFi() {
        return hasWiFi;
    }

    public boolean hasBluetooth() {
        return hasBluetooth;
    }

    @Override
    public String toString() {
        return "Computer Specification:\n" +
                " - CPU: " + CPU + "\n" +
                " - RAM: " + RAM + "\n" +
                " - Storage: " + storage + "\n" +
                " - GPU: " + (GPU != null ? GPU : "Integrated Graphics") + "\n" +
                " - WiFi: " + (hasWiFi ? "Yes" : "No") + "\n" +
                " - Bluetooth: " + (hasBluetooth ? "Yes" : "No") + "\n";
    }

    // Static nested Builder class
    public static class Builder {
        // Required attributes
        private final String CPU;
        private final String RAM;
        private final String storage;

        // Optional attributes (default values)
        private String GPU;
        private boolean hasWiFi = false;
        private boolean hasBluetooth = false;

        // Constructor for Builder with required fields
        public Builder(String CPU, String RAM, String storage) {
            this.CPU = CPU;
            this.RAM = RAM;
            this.storage = storage;
        }

        // Methods to set optional attributes
        public Builder setGPU(String GPU) {
            this.GPU = GPU;
            return this; // returns the builder instance for chaining
        }

        public Builder setWiFi(boolean hasWiFi) {
            this.hasWiFi = hasWiFi;
            return this;
        }

        public Builder setBluetooth(boolean hasBluetooth) {
            this.hasBluetooth = hasBluetooth;
            return this;
        }

        // build() method returning Computer instance
        public Computer build() {
            return new Computer(this);
        }
    }
}
