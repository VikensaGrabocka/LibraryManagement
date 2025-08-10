package controllers;

import dao.BookDAO;
import dao.BookDAOImpl;
import dao.ChapterDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Book;
import models.Chapter;

import java.io.IOException;
import java.util.Optional;


public class DeleteController {

    @FXML
    private Label success;

    @FXML
    private Label error;

    @FXML
    private TextField idField;

    @FXML
    private void delete() {

        String idText = idField.getText();

            if (idText == null || idText.trim().isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Input Error", "Ju lutem vendosni id e librit!");
                return;
            }

            try {
                int bookId = Integer.parseInt(idText.trim());
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

                // Optional confirmation
                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                confirm.setTitle("KONFIRMONI FSHIRJEN");
                confirm.setHeaderText("Jeni të sigurt?");
                confirm.setContentText("Ky veprim do të fshijë librin dhe kapitujt e tij.");
                Optional<ButtonType> result = confirm.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.OK) {
                    bookDAO.deleteBook(bookId);
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Libri u fshi me sukses!");
                    idField.clear(); // reset field

                }

            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Invalid Input", "ID e librit duhet të jetë një numër.");
            }
        }



    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();


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
