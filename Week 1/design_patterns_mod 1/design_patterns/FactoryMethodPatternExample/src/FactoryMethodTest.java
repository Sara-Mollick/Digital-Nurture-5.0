public class FactoryMethodTest {
    public static void main(String[] args) {
        System.out.println("=== Testing Factory Method Pattern ===");

        // Word Document Factory
        DocumentFactory wordFactory = new WordDocumentFactory();
        Document wordDoc = wordFactory.createDocument();
        wordDoc.open();
        wordDoc.close();

        System.out.println();

        // PDF Document Factory
        DocumentFactory pdfFactory = new PdfDocumentFactory();
        Document pdfDoc = pdfFactory.createDocument();
        pdfDoc.open();
        pdfDoc.close();

        System.out.println();

        // Excel Document Factory
        DocumentFactory excelFactory = new ExcelDocumentFactory();
        Document excelDoc = excelFactory.createDocument();
        excelDoc.open();
        excelDoc.close();
        
        System.out.println("\nUsing the helper processing template method in DocumentFactory:");
        pdfFactory.openAndProcessDocument();
    }
}
