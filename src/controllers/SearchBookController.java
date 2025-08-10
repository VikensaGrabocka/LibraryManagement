package controllers;

import dao.BookDAO;
import dao.BookDAOImpl;
import dao.ChapterDAO;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Book;
import models.Chapter;

import java.io.IOException;
import java.util.List;

public class SearchBookController {
    public Button backButton;
    public Pagination pagination;
    @FXML
    private RadioButton idRadio;
    @FXML private RadioButton titleRadio;
    @FXML private RadioButton authorRadio;
    @FXML private RadioButton categoryRadio;
    @FXML private RadioButton allRadio;
    @FXML private ToggleGroup searchToggle;

    @FXML private TextField idField;
    @FXML private TextField titleField;
    @FXML private TextField authorField;
    @FXML private TextField categoryField;
    @FXML private TableView<Book> booksTable;

    @FXML private TableColumn<Book, Integer> idColumn;
    @FXML private TableColumn<Book, String> titleColumn;
    @FXML private TableColumn<Book, String> authorColumn;
    @FXML private TableColumn<Book, Integer> yearColumn;
    @FXML private TableColumn<Book, String> categoryColumn;
    @FXML private TableColumn<Book, Integer> numberColumn;
    @FXML private TableColumn<Book, Integer> shelfNumberColumn;
    @FXML private TableColumn<Book, String> positionColumn;

    @FXML
    private Button searchButton;


    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("bookNumber"));
        shelfNumberColumn.setCellValueFactory(new PropertyValueFactory<>("shelfNumber"));
        positionColumn.setCellValueFactory(new PropertyValueFactory<>("shelfName"));


        allRadio.setSelected(true);
        idField.setVisible(false); // hidden by default
        titleField.setVisible(false);
        authorField.setVisible(false);
        categoryField.setVisible(false);

        searchToggle.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
            if (newToggle != null) {
                RadioButton selected = (RadioButton) newToggle;

                idField.setVisible(selected == idRadio);
                titleField.setVisible(selected == titleRadio);
                authorField.setVisible(selected == authorRadio);
                categoryField.setVisible(selected == categoryRadio);

                // Optionally hide all if "allRadio" is selected
                if (selected == allRadio) {
                    idField.setVisible(false);
                    titleField.setVisible(false);
                    authorField.setVisible(false);
                    categoryField.setVisible(false);
                }
            }
        });

        booksTable.setRowFactory(tv -> {
            TableRow<Book> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 2) { // double-click
                    Book clickedBook = row.getItem();
                    showChaptersPopup(clickedBook);
                }
            });
            return row;
        });



    }

    @FXML
    private void showChaptersPopup(Book selectedBook) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ChaptersPopup.fxml"));
            Parent root = loader.load();

            ChaptersPopupController controller = loader.getController();

            // Load chapters for the selected book
            ChapterDAO chapterDAO = new ChapterDAO();
            List<Chapter> chapters = chapterDAO.getChaptersByBookId(selectedBook.getId());
            controller.setChapters(chapters);

            Stage stage = new Stage();
            stage.setTitle("Kapitujt e \"" + selectedBook.getTitle() + "\"");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL); // blocks interaction with other windows
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Could not open chapters popup.");
        }
    }


    @FXML
    private void handleSearch() {
        BookDAOImpl bookDAO = new BookDAOImpl(); // Or however you're accessing your DAO
        ObservableList<Book> books = FXCollections.observableArrayList();

        try {
            if (idRadio.isSelected()) {
                int bookId = Integer.parseInt(idField.getText());
                Book book = bookDAO.getBook(bookId);
                if (book != null){
                    books.add(book);
                }else {
                    showAlert("Libri nuk u gjet", "Nuk u gjet asnjë libër! ");
                }

            } else if (titleRadio.isSelected()) {
                String title = titleField.getText();
                Book book = bookDAO.getBookTitle(title);
                if (book != null){
                    books.add(book);
                }else {
                    showAlert("Libri nuk u gjet", "Nuk u gjet asnjë libër! ");
                }

            } else if (authorRadio.isSelected()) {
                String author = authorField.getText();
                Book book = bookDAO.getBookAuthor(author);
                if (book != null){
                    books.add(book);
                }else {
                    showAlert("Libri nuk u gjet", "Nuk u gjet asnjë libër! ");
                }

            } else if (categoryRadio.isSelected()) {
                String category = categoryField.getText();
                Book book = bookDAO.getBookCategory(category);
                if (book != null) {
                    books.add(book);
                }else {
                    showAlert("Libri nuk u gjet", "Nuk u gjet asnjë libër! ");
                }

            } else if (allRadio.isSelected()) {
                books.addAll(bookDAO.getAllBooks());
                if (books.isEmpty()) {
                    showAlert("Libri nuk u gjet", "Nuk u gjet asnjë libër! ");
                }
            }

            booksTable.setItems(books); // Populate the table

        } catch (NumberFormatException e) {
            showAlert("Error", "ID jo e saktë. Ju lutem vendosni një numër.");
        } catch (Exception e) {
            showAlert("Error", "An error occurred: " + e.getMessage());
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
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






}
