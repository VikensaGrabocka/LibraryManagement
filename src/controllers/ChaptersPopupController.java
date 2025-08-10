package controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.Chapter;

import java.util.List;

public class ChaptersPopupController {


        @FXML
        private TableView<Chapter> chaptersTable;
        @FXML private TableColumn<Chapter, Integer> chapterIdColumn;
        @FXML private TableColumn<Chapter, String> chapterColumn;

        public void initialize() {
            chapterIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            chapterColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        }

        public void setChapters(List<Chapter> chapters) {
            chaptersTable.setItems(FXCollections.observableArrayList(chapters));
        }

    @FXML
    private ListView<String> chapterListView;

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }





}
