import java.sql.*;

public class Simulation {

    private Connection connection;

    public Simulation(Connection conn) {
        this.connection = conn;
    }

    public void runSchedule(String[] schedule) {
        for (String action : schedule) {
            switch (action) {
                case "r1(x)":
                    readData(1); // Reading data for id=1
                    break;
                case "w2(x)":
                    writeData(1, "T2 writes"); // We write for id=1
                    break;
                case "r2(x)":
                    readData(2); // Reading data for id=2
                    break;
                case "w1(x)":
                    writeData(1, "T1 writes"); // We write for id=1
                    break;
                case "c1":
                    commitTransaction();
                    break;
                case "c2":
                    commitTransaction();
                    break;
                default:
                    System.out.println("Unknown action: " + action);
                    break;
            }
        }
    }

    public void run(String schedule) {
        String[] scheduleActions = schedule.split(" "); // Split the string into an array of commands
        runSchedule(scheduleActions);
    }

    private void readData(int id) {
        String query = "SELECT name FROM sheet5 WHERE id = ? FOR SHARE"; // We use the name column
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                System.out.println("T2 reads: " + rs.getString("name"));
            }
        } catch (SQLException e) {
            System.err.println("Error while performing action: " + e.getMessage());
        }
    }

    private void writeData(int id, String newValue) {
        String query = "SELECT name FROM sheet5 WHERE id = ? FOR UPDATE"; // We use the name column
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                System.out.println("T2 trying to update: " + rs.getString("name"));
            }
        } catch (SQLException e) {
            System.err.println("Error while performing action: " + e.getMessage());
        }

        // Выполняем запись
        String updateQuery = "UPDATE sheet5 SET name = ? WHERE id = ?";
        try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
            updateStatement.setString(1, newValue);
            updateStatement.setInt(2, id);
            updateStatement.executeUpdate();
            System.out.println("T2 successfully updated the record for id = " + id);
        } catch (SQLException e) {
            System.err.println("Error writing to database: " + e.getMessage());
        }
    }

    private void commitTransaction() {
        try {
            connection.commit();
            System.out.println("Commit completed.");
        } catch (SQLException e) {
            System.err.println("Error while committing: " + e.getMessage());
        }
    }
}
