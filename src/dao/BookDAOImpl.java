package dao;

import models.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAOImpl implements BookDAO {
    private static final String URL = "jdbc:sqlite:library.db";

    public int addBook(Book book) {
        String sql = "INSERT INTO books(title, author, book_year, category, shelf_number, shelf_name, book_number) VALUES(?, ?, ?, ?, ?, ?, ?)";
        int generatedId = -1;

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthor());
            pstmt.setInt(3, book.getYear());
            pstmt.setString(4, book.getCategory());
            pstmt.setInt(5, book.getShelfNumber());
            pstmt.setString(6, book.getShelfName());
            pstmt.setInt(7, book.getBookNumber());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                // Get last inserted ID
                try (Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery("SELECT last_insert_rowid();")) {
                    if (rs.next()) {
                        generatedId = rs.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return generatedId;
    }


    @Override
    public Book getBook(int id) {
        String sql = "SELECT * FROM books WHERE book_id = ?";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Book(rs.getInt("book_id"), rs.getString("title"), rs.getString("author"), rs.getInt("book_year"),
                        rs.getString("category"), rs.getInt("book_number"),
                        rs.getInt("shelf_number"), rs.getString("shelf_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books";

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                books.add(new Book(rs.getInt("book_id"), rs.getString("title"), rs.getString("author"), rs.getInt("book_year"),
                        rs.getString("category"), rs.getInt("book_number"),
                        rs.getInt("shelf_number"), rs.getString("shelf_name")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }


    @Override
    public void deleteBook(int id) {
        String deleteChaptersSql = "DELETE FROM chapters WHERE book_id = ?";
        String deleteBookSql = "DELETE FROM books WHERE book_id = ?";

        try (Connection conn = DriverManager.getConnection(URL)) {
            conn.setAutoCommit(false); // Transaction block

            try (PreparedStatement pstmt1 = conn.prepareStatement(deleteChaptersSql);
                 PreparedStatement pstmt2 = conn.prepareStatement(deleteBookSql)) {

                pstmt1.setInt(1, id);
                pstmt1.executeUpdate();

                pstmt2.setInt(1, id);
                pstmt2.executeUpdate();

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Book getBookTitle(String title) {
        String sql = "SELECT * FROM books WHERE title= ?";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, title);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Book(rs.getInt("book_id"), rs.getString("title"), rs.getString("author"), rs.getInt("book_year"),
                        rs.getString("category"), rs.getInt("book_number"),
                        rs.getInt("shelf_number"), rs.getString("shelf_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public Book getBookAuthor(String author) {
        String sql = "SELECT * FROM books WHERE  \"author\"= ?";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, author);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Book(rs.getInt("book_id"), rs.getString("title"), rs.getString("author"), rs.getInt("book_year"),
                        rs.getString("category"), rs.getInt("book_number"),
                        rs.getInt("shelf_number"), rs.getString("shelf_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Book getBookCategory(String category) {
        String sql = "SELECT * FROM books WHERE category= ?";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, category);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Book(rs.getInt("book_id"), rs.getString("title"), rs.getString("author"), rs.getInt("book_year"),
                        rs.getString("category"), rs.getInt("book_number"),
                        rs.getInt("shelf_number"), rs.getString("shelf_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean bookExists(int bookId) {
        String sql = "SELECT 1 FROM books WHERE book_id = ?";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, bookId);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // returns true if a row is found
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


}

