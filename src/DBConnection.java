
// Source code is decompiled from a .class file using FernFlower decompiler.
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:postgresql://vsisdb.informatik.uni-hamburg.de:5432/dis-2025";
    private static final String USER = "vsisp88";
    private static final String PASSWORD = "k7GtQpvK";

    public DBConnection() {
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://vsisdb.informatik.uni-hamburg.de:5432/dis-2025",
                "vsisp88", "k7GtQpvK");
    }
}
