package dao;

import models.Loan;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoanDAO {
    private static final String DB_URL = "jdbc:sqlite:library.db";
    public void addLoan(Loan loan) {
        String sql = "INSERT INTO loans (book_id, borrower_name, borrower_surname, start_date, return_date) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, loan.getBookId());
            pstmt.setString(2, loan.getBorrowerName());
            pstmt.setString(3, loan.getBorrowerSurname());
            pstmt.setString(4, loan.getStartDate());
            pstmt.setString(5, loan.getReturnDate());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Loan> getAllLoans() {
        List<Loan> loans = new ArrayList<>();
        String sql = "SELECT * FROM loans";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Loan loan = new Loan();
                loan.setId(rs.getInt("id"));
                loan.setBookId(rs.getInt("book_id"));
                loan.setBorrowerName(rs.getString("borrower_name"));
                loan.setBorrowerSurname(rs.getString("borrower_surname"));
                loan.setStartDate(rs.getString("start_date"));
                loan.setReturnDate(rs.getString("return_date"));
                loans.add(loan);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return loans;
    }

    public void deleteLoan(int id) {
        String sql = "DELETE FROM loans WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateReturnDate(int bookId, String newReturnDate) {
        String sql = "UPDATE loans SET return_date = ? WHERE book_id = ? AND return_date IS NULL";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newReturnDate);
            pstmt.setInt(2, bookId);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
