package dao;

import models.Chapter;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChapterDAO {
    private static final String URL = "jdbc:sqlite:library.db";

    public void addChapter(Chapter chapter) {
        String sql = "INSERT INTO chapters (title, book_id) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, chapter.getTitle());
            pstmt.setInt(2, chapter.getBookId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Chapter> getChaptersByBookId(int bookId) {
        List<Chapter> chapters = new ArrayList<>();
        String sql = "SELECT * FROM chapters WHERE book_id = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, bookId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Chapter chapter = new Chapter();
                chapter.setId(rs.getInt("id"));
                chapter.setTitle(rs.getString("title"));
                chapter.setBookId(bookId);
                chapters.add(chapter);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return chapters;
    }

    public void deleteChapter(int id) {
        String sql = "DELETE FROM chapters WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Chapter> getChaptersByBookName(String bookName) {
        List<Chapter> chapters = new ArrayList<>();
        String sql = """
        SELECT chapters.id, chapters.title, chapters.book_id
        FROM chapters
        JOIN books ON chapters.book_id = books.id
        WHERE books.name = ?
    """;

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, bookName);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Chapter chapter = new Chapter();
                chapter.setId(rs.getInt("id"));
                chapter.setTitle(rs.getString("title"));
                chapter.setBookId(rs.getInt("book_id"));
                chapters.add(chapter);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return chapters;
    }

}
