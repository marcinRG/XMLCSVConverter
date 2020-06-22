package io.marcinrg;

import io.marcinrg.collections.FileCollection;
import io.marcinrg.collections.PersonCollection;
import io.marcinrg.model.Person;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.File;
import java.util.EventObject;

public class MainAppController {
    private DirectoryChooser getFiles = new DirectoryChooser();
    private FileChooser saveFile = new FileChooser();

    private FileCollection fileCollection = new FileCollection();
    private PersonCollection personCollection = new PersonCollection();
    private String labelType = "Wybrany typ pliku: ";
    private String labelFormat = "Wybrany format pliku: ";

    private final String labelZUSType = "Plik ZUS";
    private final String labelPITType = "Plik PIT-8";

    @FXML
    private TableView<File> tableViewFiles = new TableView<>();
    private TableColumn<File, String> fileNameColumn = new TableColumn<>("Nazwa pliku");
    private TableColumn<File, String> filePathColumn = new TableColumn<>("Ścieżka");

    @FXML
    private TableView<Person> tableViewPersons = new TableView<>();


    //private TableView<PersonSimple> tableViewPersons = new TableView<>();
//    private ObservableList<PersonSimple> personsExpList = FXCollections.observableArrayList(
//            new PersonSimple("janek", "kowalski"),
//            new PersonSimple("ben", "dżonson"),
//            new PersonSimple("john", "doe")
//    );
//    private ObservableList<PersonExp> personsExpList = FXCollections.observableArrayList(
//            new PersonExp("janek", "kowalski", "e@e.com"),
//            new PersonExp("ben", "dżonson", "e@e.com"),
//            new PersonExp("john", "doe", "e@e.com")
//    );


    @FXML
    private Label labelSelectedFileType;
    @FXML
    private Label labelSelectedFileFormat;
    @FXML
    private RadioMenuItem XMLZusRadioItem;
    @FXML
    private RadioMenuItem XMLPITRadioItem;

    @FXML
    private RadioMenuItem PIT2017RadioItem;
    @FXML
    private RadioMenuItem PIT2018RadioItem;
    @FXML
    private RadioMenuItem PIT2019RadioItem;
    @FXML
    private RadioMenuItem PIT2020RadioItem;


    public MainAppController() {
    }

    private void prepareTableViewFiles() {
        tableViewFiles.getColumns().addAll(fileNameColumn, filePathColumn);
        fileNameColumn.setCellValueFactory(fileStringCellDataFeatures -> new SimpleStringProperty(fileStringCellDataFeatures.getValue().getName()));
        filePathColumn.setCellValueFactory(fileStringCellDataFeatures -> new SimpleStringProperty(fileStringCellDataFeatures.getValue().getAbsolutePath()));
        tableViewFiles.setItems(fileCollection.getFileList());
    }

    @FXML
    private void initialize() {
        setDirectoryChooserOptions();
        setFileChooserOptions();
        XMLZusRadioItem.setSelected(true);
        XMLZusRadioItem.setText(labelZUSType);
        XMLPITRadioItem.setText(labelPITType);
        labelSelectedFileType.setText(labelType + labelZUSType);
        setEnabledFileFormatRadioItems("");

        prepareTableViewFiles();

    }

    private void setEnabledFileFormatRadioItems(String name) {
        switch (name) {
            case labelPITType: {
                PIT2017RadioItem.setDisable(false);
                PIT2018RadioItem.setDisable(false);
                PIT2019RadioItem.setDisable(false);
                PIT2020RadioItem.setDisable(false);
                break;
            }
            default: {
                PIT2017RadioItem.setDisable(true);
                PIT2018RadioItem.setDisable(true);
                PIT2019RadioItem.setDisable(true);
                PIT2020RadioItem.setDisable(true);
            }
        }
    }

    private void setDirectoryChooserOptions() {
        getFiles.setTitle("Otwórz katalog z plikami .xml do przetworzenia");
        getFiles.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
    }

    private void setFileChooserOptions() {
        saveFile.setTitle("Zapisz osoby");
        saveFile.setInitialDirectory(new File(System.getProperty("user.home")));
        saveFile.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("wszystkie pliki", "*.*"),
                new FileChooser.ExtensionFilter("csv", "*.csv")
        );
    }

    @FXML
    private void loadFiles(ActionEvent actionEvent) throws Exception {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        File f = getFiles.showDialog(stage);
        if (f != null && f.exists() && f.isDirectory()) {
            fileCollection.getFilesFromDirectory(f);
        }
    }

    @FXML
    private void saveFiles(ActionEvent actionEvent) {
        if (personCollection.getPersonsList().size() > 0) {
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            File f = saveFile.showSaveDialog(stage);
//            if (f != null) {
//                boolean saved = FileSaver.saveToFile(f, personCollection.getPersonsList());
//            }
        }
    }

    @FXML
    private void changeFileType(ActionEvent event) {
        String txt = ((RadioMenuItem) event.getSource()).getText();
        labelSelectedFileType.setText(labelType + txt);
        setEnabledFileFormatRadioItems(txt);
    }

    @FXML
    private void changeFileFormat(ActionEvent event) {
        String txt = ((RadioMenuItem) event.getSource()).getText();
        labelSelectedFileFormat.setText(labelFormat + txt);
        setEnabledFileFormatRadioItems(txt);
    }

    @FXML
    private void parsePersons() {

//        TableColumn<PersonSimple, String> firstNameCol = new TableColumn("First Name");
//        firstNameCol.setMinWidth(100);
//        firstNameCol.setCellValueFactory(
//                new PropertyValueFactory<PersonSimple, String>("firstName"));
//
//        TableColumn<PersonSimple, String> lastNameCol = new TableColumn("Last Name");
//        lastNameCol.setMinWidth(100);
//        lastNameCol.setCellValueFactory(
//                new PropertyValueFactory<PersonSimple, String>("lastName"));
//
//        tableViewPersons.setItems(personsExpList);
//        tableViewPersons.getColumns().addAll(firstNameCol, lastNameCol);


    }

    @FXML
    private void clearPersons() {
        //personCollection.clear();
    }

    @FXML
    private void clearFiles() {
        fileCollection.clear();
    }

    @FXML
    private void closeApplication() {
        Platform.exit();
    }
}
