public class Logger {
    // Private static instance of itself
    private static Logger instance;

    // Ensure the constructor of Logger is private
    private Logger() {
        // Prevent instantiation from outside
    }

    // Provide a public static method to get the instance of the Logger class
    // Using double-checked locking for thread-safety and efficiency
    public static Logger getInstance() {
        if (instance == null) {
            synchronized (Logger.class) {
                if (instance == null) {
                    instance = new Logger();
                }
            }
        }
        return instance;
    }

    // Simple logging utility method
    public void log(String message) {
        System.out.println("[LOG] " + java.time.LocalDateTime.now() + " - " + message);
    }
}
