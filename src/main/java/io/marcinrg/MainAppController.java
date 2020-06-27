package io.marcinrg;

import io.marcinrg.collections.FileCollection;
import io.marcinrg.collections.PersonCollection;
import io.marcinrg.model.FileWithPOM;
import io.marcinrg.model.Person;
import io.marcinrg.xml.PersonPIT2019Handler;
import io.marcinrg.xml.PersonZUSHandler;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.File;


public class MainAppController {
    private DirectoryChooser getFiles = new DirectoryChooser();
    private FileChooser saveFile = new FileChooser();
    private PersonPIT2019Handler personPIT2019Handler = new PersonPIT2019Handler();
    private PersonZUSHandler personZUSHandler = new PersonZUSHandler();

    private FileCollection fileCollection = new FileCollection();
    private PersonCollection personCollection = new PersonCollection();
    private String labelType = "Wybrany typ pliku: ";
    private String labelFormat = "Wybrany format pliku: ";

    private final String labelZUSType = "Plik ZUS";
    private final String labelPITType = "Plik PIT-8";

    @FXML
    private TableView<FileWithPOM> tableViewFiles = new TableView<>();
    private TableColumn<FileWithPOM, String> fileNameColumn = new TableColumn<>("Nazwa pliku");
    private TableColumn<FileWithPOM, String> filePathColumn = new TableColumn<>("Ścieżka");
    private TableColumn<FileWithPOM, Boolean> filePOMColumn = new TableColumn<>("POM");

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

    private void prepareTableViewPersons() {
        TableColumn<Person, String> nameColumn = new TableColumn<>("Imię");
        TableColumn<Person, String> surNameColumn = new TableColumn<>("Nazwisko");
        TableColumn<Person, String> dataColumn = new TableColumn<>("Dane");

        tableViewPersons.getColumns().addAll(nameColumn, surNameColumn, dataColumn);
        nameColumn.setCellValueFactory(personStringCellDataFeatures -> new SimpleStringProperty(personStringCellDataFeatures.getValue().getName()));
        surNameColumn.setCellValueFactory(personStringCellDataFeatures -> new SimpleStringProperty(personStringCellDataFeatures.getValue().getSurName()));
        dataColumn.setCellValueFactory(personStringCellDataFeatures -> new SimpleStringProperty(personStringCellDataFeatures.getValue().getData("|")));
        tableViewPersons.setItems(personCollection.getPersonsList());
        tableViewPersons.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private void prepareTableViewFiles() {
        tableViewFiles.getColumns().addAll(fileNameColumn, filePathColumn, filePOMColumn);
        fileNameColumn.setCellValueFactory((TableColumn.CellDataFeatures<FileWithPOM, String> fileStringCellDataFeatures) -> new SimpleStringProperty(fileStringCellDataFeatures.getValue().getFile().getName()));
        filePathColumn.setCellValueFactory(fileStringCellDataFeatures -> new SimpleStringProperty(fileStringCellDataFeatures.getValue().getFile().getAbsolutePath()));

        filePOMColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FileWithPOM, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<FileWithPOM, Boolean> fileWithPOMBooleanCellDataFeatures) {
                return new ReadOnlyBooleanWrapper(fileWithPOMBooleanCellDataFeatures.getValue().isHasPOM());
            }
        });
        filePOMColumn.setCellFactory(new Callback<TableColumn<FileWithPOM, Boolean>, TableCell<FileWithPOM, Boolean>>() {
            @Override
            public TableCell<FileWithPOM, Boolean> call(TableColumn<FileWithPOM, Boolean> fileWithPOMBooleanTableColumn) {
                return new CheckBoxTableCell();
            }
        });
        tableViewFiles.setItems(fileCollection.getFileList());
        tableViewFiles.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
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
        prepareTableViewPersons();

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
            if (f != null) {
                boolean saved = personCollection.saveToFile(f);
                if (!saved) {
                    System.out.println("Did not save to file");
                }
            }
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
        personCollection.getPersonsFromZUSXMLFile(fileCollection);
    }

    @FXML
    private void clearPersons() {
        personCollection.clear();
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
