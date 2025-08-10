package library;

import java.sql.*;

public class DatabaseInitializer {
    private static final String DB_URL = "jdbc:sqlite:library.db"; // Creates file in project root

    public static void initialize() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {

            // Enable foreign keys
            stmt.execute("PRAGMA foreign_keys = ON");

            stmt.execute("""
    CREATE TABLE IF NOT EXISTS books (
        book_id INTEGER PRIMARY KEY AUTOINCREMENT,
        title TEXT NOT NULL,
        author TEXT NOT NULL,
        book_year INTEGER NOT NULL,
        category TEXT NOT NULL,
        book_number INTEGER NOT NULL,
        shelf_number INTEGER NOT NULL,
        shelf_name TEXT NOT NULL
    );
""");


            // Create Chapters table with foreign key to books
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS chapters (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    title TEXT NOT NULL,
                     book_id INTEGER NOT NULL,
                    FOREIGN KEY (book_id) REFERENCES books(book_id)
                );
            """);

            // Create models.Loan table with foreign key to books
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS loans (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    book_id INTEGER,
                    borrower_name TEXT NOT NULL,
                    borrower_surname TEXT NOT NULL,
                    start_date TEXT NOT NULL,
                    return_date TEXT,
                    FOREIGN KEY (book_id) REFERENCES books(book_id)
                );
            """);

            System.out.println("Database and tables created successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

