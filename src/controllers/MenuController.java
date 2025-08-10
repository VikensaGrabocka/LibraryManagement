package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Objects;

//public class MenuController {
//    @FXML
//    private Button addField;
//
//    @FXML
//    private Button  deleteField;
//
//    @FXML
//    private Button  loanField;
//
//    @FXML
//    private Button  searchField;
//
//    // Go to Search View when clicking search text field
//    @FXML
//    private void goToSearchView(MouseEvent event) {
//        loadView("SearchBook.fxml");
//    }
//
//    // Go to Loan View
//    @FXML
//    private void goToLoanView(ActionEvent event) {
//        loadView("LoanBook.fxml");
//    }
//
//    // Go to Delete View
//    @FXML
//    private void goToDeleteView(ActionEvent event) {
//        loadView("DeleteBook.fxml");
//    }
//
//    @FXML
//    private void goToAddView(ActionEvent event) {
//        loadView("AddBook.fxml");
//    }
//
//    // Reusable method to load a view
//    private void loadView(String fxml) {
//        try {
//            Parent root = FXMLLoader.load(getClass().getResource("/views/" + fxml));
//            Stage stage = (Stage) someButton.getScene().getWindow(); // Use known node here
//            Scene scene = new Scene(root);
//            stage.setScene(scene);
//            stage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;  // to cast event source

public class MenuController {

    @FXML
    private void goToSearchView(ActionEvent event) {
        loadView(event, "SearchBook.fxml");
    }

    @FXML
    private void goToAddView(ActionEvent event) {
        loadView(event, "AddBook.fxml");
    }

    @FXML
    private void goToLoanView(ActionEvent event) {
        loadView(event, "LoanBook.fxml");
    }

    @FXML
    private void goToDeleteView(ActionEvent event) {
        loadView(event, "DeleteBook.fxml");
    }

    // Reusable method to load a view given the event and FXML filename
    private void loadView(ActionEvent event, String fxmlFile) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/views/" + fxmlFile)));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();  // get stage from event source
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
