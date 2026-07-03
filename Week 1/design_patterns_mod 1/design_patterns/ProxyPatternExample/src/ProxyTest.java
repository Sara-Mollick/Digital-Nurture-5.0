public class ProxyTest {
    public static void main(String[] args) {
        System.out.println("=== Testing Proxy Pattern ===");

        // Create proxies for two images
        Image image1 = new ProxyImage("photo_beach_4k.jpg");
        Image image2 = new ProxyImage("screenshot_dashboard_1080p.png");

        System.out.println("--- Scenario 1: Images registered, but not loaded yet ---");
        // No loading output should appear here

        System.out.println("\n--- Scenario 2: Displaying image1 for the first time ---");
        image1.display(); // triggers remote loading

        System.out.println("\n--- Scenario 3: Displaying image1 for the second time ---");
        image1.display(); // should retrieve from cache, no remote loading

        System.out.println("\n--- Scenario 4: Displaying image2 for the first time ---");
        image2.display(); // triggers remote loading for image2
        
        System.out.println("\n--- Scenario 5: Displaying image2 for the second time ---");
        image2.display(); // retrieves from cache
    }
}
