public class SingletonTest {
    public static void main(String[] args) {
        System.out.println("=== Testing Singleton Pattern ===");

        // Retrieve two instances of Logger
        Logger logger1 = Logger.getInstance();
        Logger logger2 = Logger.getInstance();

        // Use the instances to log messages
        logger1.log("First log message via logger1.");
        logger2.log("Second log message via logger2.");

        // Check if both references point to the same instance
        System.out.println("Reference logger1: " + logger1);
        System.out.println("Reference logger2: " + logger2);
        System.out.println("Are both references identical? " + (logger1 == logger2));

        if (logger1 == logger2) {
            System.out.println("SUCCESS: Only one instance of Logger exists in the application.");
        } else {
            System.out.println("FAILURE: Multiple instances of Logger were created!");
        }
    }
}
