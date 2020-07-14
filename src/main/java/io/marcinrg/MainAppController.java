package io.marcinrg;

import io.marcinrg.collections.FileCollection;
import io.marcinrg.collections.PersonCollection;
import io.marcinrg.enums.FileTypesNames;
import io.marcinrg.enums.PitType;
import io.marcinrg.factories.MenuItemsFactory;
import io.marcinrg.model.AppState;
import io.marcinrg.model.FileWithPOM;
import io.marcinrg.model.Person;
import io.marcinrg.utils.FileSaver;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;


public class MainAppController {

    private AppState appState = new AppState();
    private DirectoryChooser getFiles = new DirectoryChooser();
    private FileChooser saveFile = new FileChooser();
    private FileCollection fileCollection = new FileCollection();
    private PersonCollection personCollection = new PersonCollection();

//    private String labelType = "Wybrany typ pliku: ";
//    private String labelFormat = "Wybrany format pliku: ";

    @FXML
    BorderPane appWindow;
    @FXML
    Menu fileOptions;
    @FXML
    private TableView<FileWithPOM> tableViewFiles = new TableView<>();
    @FXML
    private TableView<Person> tableViewPersons = new TableView<>();

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
        prepareTableViewFiles();
        prepareTableViewPersons();
        createFilesMenu();


        appState.selectedFileTypeProperty().addListener(new ChangeListener<FileTypesNames>() {
            @Override
            public void changed(ObservableValue<? extends FileTypesNames> observableValue, FileTypesNames oldValue, FileTypesNames newValue) {
                System.out.println(newValue.getFileType());
            }
        });


    }

    private void createFilesMenu() {
        ToggleGroup toggleGroup = new ToggleGroup();
        for (FileTypesNames file : FileTypesNames.values()) {
            fileOptions.getItems().add(MenuItemsFactory.createRadioMenuItemForFileOptionMenu(file, toggleGroup, appState.selectedFileTypeProperty()));
        }
        Menu pitTypesMenu = new Menu("XML PIT-11 rodzaje");
        ToggleGroup pityToggleGroup = new ToggleGroup();
        for (PitType type : PitType.values()) {
            RadioMenuItem radioMenuItem = new RadioMenuItem(type.toString());
            radioMenuItem.setToggleGroup(pityToggleGroup);
            pitTypesMenu.getItems().add(radioMenuItem);
        }
        pitTypesMenu.disableProperty().bind(appState.pitFileDisabledProperty());
        fileOptions.getItems().add(new SeparatorMenuItem());
        fileOptions.getItems().add(pitTypesMenu);
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
                boolean saved = FileSaver.saveToFile(f, personCollection.getPersonsList(), "|", true);
                if (!saved) {
                    System.out.println("Did not save to file");
                }
            }
        }
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
