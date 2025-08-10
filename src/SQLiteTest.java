//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.sql.Connection;
import java.sql.DriverManager;

public class SQLiteTest {
    public static void main(String[] args) {
        try {
            // Load the driver (optional in modern Java)
            Class.forName("org.sqlite.JDBC");

            Connection conn = DriverManager.getConnection("jdbc:sqlite:test.sqlite");
            System.out.println("Connection successful.");
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
