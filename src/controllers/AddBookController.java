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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Book;
import models.Chapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddBookController {
    @FXML
    private VBox chapterListVBox;

    @FXML
    private TextField titleTextField;

    @FXML
    private TextField authorTextField;

    @FXML
    private TextField yearTextField;

    @FXML
    private TextField categoryTextField;

    @FXML
    private TextField bookNumberTextField;

    @FXML
    private TextField shelfNumberTextField;

    @FXML
    private TextField shelfNameTextField;

    @FXML
    private Label success;

    @FXML
    private Label error;


    @FXML
    private void onAddChapter() {
        TextField chapterField = new TextField();
        chapterField.setPromptText("Vendosni titullin e kapitullit");
        chapterListVBox.getChildren().add(chapterField);
    }

    @FXML
    private void save() {
        error.setVisible(false);
        success.setVisible(false);
        // 1. Create Book object from form fields
        Book book = new Book(
                titleTextField.getText(),
                authorTextField.getText(),
                Integer.parseInt(yearTextField.getText()),
                categoryTextField.getText(),
                Integer.parseInt(bookNumberTextField.getText()),
                Integer.parseInt(shelfNumberTextField.getText()),
                shelfNameTextField.getText()
        );

        // 2. Save the book and get the generated book ID
        BookDAO bookDAO = new BookDAOImpl();
        int bookId = bookDAO.addBook(book);
        if (bookId == -1) {
            System.out.println("Failed to insert book.");
            error.setVisible(true);
            return;
        }

        // 3. Get chapter titles from UI
        List<String> chapterTitles = new ArrayList<>();
        for (Node node : chapterListVBox.getChildren()) {
            if (node instanceof TextField tf && !tf.getText().isEmpty()) {
                chapterTitles.add(tf.getText());
            }
        }

        // 4. Insert chapters
        for (String chapterTitle : chapterTitles) {
            Chapter chapter = new Chapter(chapterTitle, bookId);
            ChapterDAO chapterDao = new ChapterDAO();
            chapterDao.addChapter(chapter);
        }

        success.setVisible(true);
        System.out.println("Book and chapters saved successfully.");
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
