import java.sql.Connection;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        DBConnection dbConnection = new DBConnection();
        Scanner scanner = new Scanner(System.in);
        Connection conn = null;

        try {
            conn = dbConnection.getConnection();
            conn.setAutoCommit(false); // Disabling autocommit

            System.out.println("Select schedule:");
            System.out.println("1 - r1(x) w2(x) c2 w1(x) r1(x) c1");
            System.out.println("2 - r1(x) w2(x) c2 r1(x) c1");
            System.out.println("3 - r2(x) w1(x) w1(y) c1 r2(y) w2(x) w2(y) c2");
            System.out.print("Enter schedule number (1-3): ");
            int scheduleNumber = scanner.nextInt();

            String schedule = "";

            switch (scheduleNumber) {
                case 1:
                    schedule = "r1(x) w2(x) c2 w1(x) r1(x) c1";
                    break;
                case 2:
                    schedule = "r1(x) w2(x) c2 r1(x) c1";
                    break;
                case 3:
                    schedule = "r2(x) w1(x) w1(y) c1 r2(y) w2(x) w2(y) c2";
                    break;
                default:
                    System.out.println("Invalid schedule number.");
                    return;
            }

            // Create a Simulation instance and execute the selected schedule
            Simulation sim = new Simulation(conn);
            System.out.println("\n--- Execute the selected scenario with locks ---");
            sim.run(schedule);

            // Коммитим транзакцию
            conn.commit();
            System.out.println("The scenario is completed. Return to the main menu...");

        } catch (Exception e) {
            System.err.println("Error while executing: " + e.getMessage());
            try {
                if (conn != null)
                    conn.rollback(); // Roll back the transaction in case of an error
            } catch (Exception ex) {
                System.err.println("Error during rollback: " + ex.getMessage());
            }
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (Exception e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
            scanner.close();
        }
    }
}
