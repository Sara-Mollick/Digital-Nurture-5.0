public class ProxyImage implements Image {
    private final String filename;
    private RealImage realImage;

    public ProxyImage(String filename) {
        this.filename = filename;
    }

    @Override
    public void display() {
        // Lazy initialization: Instantiate RealImage only when it needs to be displayed
        if (realImage == null) {
            realImage = new RealImage(filename);
        } else {
            System.out.println("Retrieving '" + filename + "' from cache...");
        }
        // Delegate display to RealImage
        realImage.display();
    }
}
