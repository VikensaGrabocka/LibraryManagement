package controllers;

import dao.BookDAOImpl;
import dao.LoanDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Loan;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class LoanBookController {

    @FXML
    private TextField id;

    @FXML
    private TextField name;

    @FXML
    private TextField surname;

    @FXML
    private DatePicker start;

    @FXML
    private DatePicker end;

    @FXML
    private TableView<Loan> loanTable;

    @FXML
    private TableColumn<Loan, Integer> idColumn;

    @FXML
    private TableColumn<Loan, Integer> bookIdColumn;

    @FXML
    private TableColumn<Loan, String> nameColumn;

    @FXML
    private TableColumn<Loan, String> surnameColumn;

    @FXML
    private TableColumn<Loan, String> startColumn;

    @FXML
    private TableColumn<Loan, String> returnColumn;


    @FXML
    public void save() {
        try {
            int bookId = Integer.parseInt(id.getText());
            String borrowerName = name.getText();
            String borrowerSurname = surname.getText();
            DatePicker startDatePicker = new DatePicker();
            LocalDate selectedDate = start.getValue();
            DatePicker returnDatePicker = new DatePicker();
            LocalDate returnDateSelected = end.getValue();
            String startDate = (selectedDate != null) ? selectedDate.toString() : "";
            String returnDate = (returnDateSelected != null) ? returnDateSelected.toString() : "";


            BookDAOImpl bookDAO = new BookDAOImpl();
            if (!bookDAO.bookExists(bookId)) {
                // Show an alert if the book does not exist
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Libri nuk u gjet!");
                alert.setHeaderText(null);
                alert.setContentText("Libri me ID" + bookId + " nuk ekziston.");
                alert.showAndWait();
                return;
            }


            Loan loan = new Loan(bookId, borrowerName, borrowerSurname, startDate, returnDate);

            LoanDAO loanDAO = new LoanDAO(); // Or inject if you're using DI
            loanDAO.addLoan(loan);

            // Optionally show confirmation
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Huazimi u ruajt me sukses!");
            alert.showAndWait();

            // Optionally clear form
            id.clear();
            name.clear();
            surname.clear();

        } catch (NumberFormatException e) {
            showError("ID e librit duhet të jetë një numër.");
        } catch (Exception e) {
            showError("An error occurred: " + e.getMessage());
        }
    }

    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        bookIdColumn.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("borrowerName"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("borrowerSurname"));
        startColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        returnColumn.setCellValueFactory(new PropertyValueFactory<>("returnDate"));

    }

    @FXML
    private void showLoans() {
        LoanDAO dao = new LoanDAO();
        List<Loan> loans = dao.getAllLoans();

        ObservableList<Loan> observableList = FXCollections.observableArrayList(loans);
        loanTable.setItems(observableList);
    }


    @FXML
    private void handleUpdateReturnDate(ActionEvent event) {
        Loan selectedLoan = loanTable.getSelectionModel().getSelectedItem();

        if (selectedLoan == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Ju lutem selektoni një huazim.");
            return;
        }

        // Create a dialog with a DatePicker
        Dialog<LocalDate> dialog = new Dialog<>();
        dialog.setTitle("Modifikoni datën e kthimit");
        dialog.setHeaderText("Selektoni datën e kthimit");

        ButtonType confirmButtonType = new ButtonType("Update", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(confirmButtonType, ButtonType.CANCEL);

        DatePicker datePicker = new DatePicker();
        VBox content = new VBox(new Label("Data e kthimit:"), datePicker);
        content.setSpacing(10);
        dialog.getDialogPane().setContent(content);

        // Convert result to LocalDate
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == confirmButtonType) {
                return datePicker.getValue();
            }
            return null;
        });

        Optional<LocalDate> result = dialog.showAndWait();

        result.ifPresent(newDate -> {
            if (newDate == null) {
                showAlert(Alert.AlertType.ERROR, "Invalid Date", "Ju lutem selektoni një datë të vlefshme.");
                return;
            }

            // Format the date to yyyy-MM-dd
            String formattedDate = newDate.toString();

            // Call your update method

            LoanDAO loanDAO = new LoanDAO();
            loanDAO.updateReturnDate(selectedLoan.getBookId(), formattedDate);

            // Refresh the table
            loadLoansIntoTable();
        });
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();


    }
    private void loadLoansIntoTable() {
        LoanDAO loanDAO = new LoanDAO();
        List<Loan> loans = loanDAO.getAllLoans();
        ObservableList<Loan> data = FXCollections.observableArrayList(loans);
        loanTable.setItems(data);
    }

    @FXML
    private void goBack(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Menu.fxml"));
            Parent menuRoot = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene menuScene = new Scene(menuRoot);
            stage.setScene(menuScene);
            stage.setTitle("Menu");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load the menu view.");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }





}

