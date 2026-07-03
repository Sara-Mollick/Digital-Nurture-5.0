public class RealImage implements Image {
    private final String filename;

    public RealImage(String filename) {
        this.filename = filename;
        loadImageFromServer();
    }

    private void loadImageFromServer() {
        System.out.println("Loading image '" + filename + "' from remote server... (this takes significant bandwidth/time)");
        try {
            Thread.sleep(1000); // Simulate network latency
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Image '" + filename + "' successfully loaded and cached in memory.");
    }

    @Override
    public void display() {
        System.out.println("Displaying high-resolution image: " + filename);
    }
}
