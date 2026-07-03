public abstract class DocumentFactory {
    // Factory method
    public abstract Document createDocument();

    // An optional helper method that uses the factory method
    public void openAndProcessDocument() {
        Document doc = createDocument();
        doc.open();
        System.out.println("Processing document details...");
        doc.close();
    }
}
