public class BuilderTest {
    public static void main(String[] args) {
        System.out.println("=== Testing Builder Pattern ===");

        // 1. Basic configuration (only required parameters)
        Computer officePC = new Computer.Builder("Intel Core i3", "8GB DDR4", "256GB SSD")
                .build();
        System.out.println("Office PC spec:");
        System.out.println(officePC);

        // 2. Mid-range configuration (with some optional features)
        Computer gamingPC = new Computer.Builder("AMD Ryzen 7", "16GB DDR4", "1TB NVMe SSD")
                .setGPU("NVIDIA RTX 4070")
                .setWiFi(true)
                .build();
        System.out.println("Gaming PC spec:");
        System.out.println(gamingPC);

        // 3. High-end configuration (all optional features enabled)
        Computer workstation = new Computer.Builder("Intel Core i9", "64GB DDR5", "2TB NVMe SSD + 4TB HDD")
                .setGPU("NVIDIA RTX 4090")
                .setWiFi(true)
                .setBluetooth(true)
                .build();
        System.out.println("Workstation spec:");
        System.out.println(workstation);
    }
}
