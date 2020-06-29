package io.marcinrg;

import io.marcinrg.collections.FileCollection;
import io.marcinrg.collections.PersonCollection;
import io.marcinrg.model.FileWithPOM;
import io.marcinrg.model.Person;
import io.marcinrg.utils.FileSaver;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;


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
    private TableView<FileWithPOM> tableViewFiles = new TableView<>();
    @FXML
    private TableView<Person> tableViewPersons = new TableView<>();
    @FXML
    private Label labelSelectedFileType;
    @FXML
    private Label labelSelectedFileFormat;
    @FXML
    private RadioMenuItem XMLZusRadioItem;
    @FXML
    private RadioMenuItem XMLPITRadioItem;
    @FXML
    private RadioMenuItem PIT2019RadioItem;



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
        TableColumn<FileWithPOM, String> fileNameColumn = new TableColumn<>("Nazwa pliku");
        TableColumn<FileWithPOM, String> filePathColumn = new TableColumn<>("Ścieżka");
        TableColumn<FileWithPOM, Boolean> filePOMColumn = new TableColumn<>("BOM");
        tableViewFiles.getColumns().addAll(fileNameColumn, filePathColumn, filePOMColumn);
        fileNameColumn.setCellValueFactory((TableColumn.CellDataFeatures<FileWithPOM, String> fileStringCellDataFeatures) -> new SimpleStringProperty(fileStringCellDataFeatures.getValue().getFile().getName()));
        filePathColumn.setCellValueFactory(fileStringCellDataFeatures -> new SimpleStringProperty(fileStringCellDataFeatures.getValue().getFile().getAbsolutePath()));

        filePOMColumn.setCellValueFactory(fileWithPOMBooleanCellDataFeatures -> new ReadOnlyBooleanWrapper(fileWithPOMBooleanCellDataFeatures.getValue().isHasPOM()));
        filePOMColumn.setCellFactory(fileWithPOMBooleanTableColumn -> new CheckBoxTableCell());
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
                PIT2019RadioItem.setDisable(false);
                break;
            }
            default: {
                PIT2019RadioItem.setDisable(true);
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
                boolean saved = FileSaver.saveToFile(f, personCollection.getPersonsList(),"|", true);
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
