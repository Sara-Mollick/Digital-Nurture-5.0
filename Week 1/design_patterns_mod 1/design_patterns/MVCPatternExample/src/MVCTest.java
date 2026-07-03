public class MVCTest {
    public static void main(String[] args) {
        System.out.println("=== Testing MVC Pattern ===");

        // Fetch student record (e.g. from database, initialized manually here)
        Student studentModel = retrieveStudentFromDatabase();

        // Create a view to show student details
        StudentView view = new StudentView();

        // Initialize controller
        StudentController controller = new StudentController(studentModel, view);

        // Display initial details
        System.out.println("Initial View:");
        controller.updateView();

        // Update model details via controller
        System.out.println("\nUpdating model details via Controller...");
        controller.setStudentName("Jane Doe");
        controller.setStudentGrade("A+");

        // Display updated details
        System.out.println("\nUpdated View:");
        controller.updateView();
    }

    private static Student retrieveStudentFromDatabase() {
        Student student = new Student();
        student.setName("John Smith");
        student.setId("S12345");
        student.setGrade("B");
        return student;
    }
}
