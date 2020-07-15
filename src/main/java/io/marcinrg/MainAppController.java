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

    private final String labelType = "Wybrany typ pliku: ";
    private final String labelFormat = "Wybrany format pliku: ";

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
    }

    private void showInfoDialog(String info, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Uwaga: Informacja!");
        alert.setHeaderText(info);
        alert.setContentText(msg);
        alert.show();
    }

    private void showWarningDialog(String info, String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Uwaga: Wystąpił błąd!");
        alert.setHeaderText(info);
        alert.setContentText(msg);
        alert.show();
    }


    private void createFilesMenu() {
        ToggleGroup toggleGroup = new ToggleGroup();
        for (FileTypesNames file : FileTypesNames.values()) {
            fileOptions.getItems().add(MenuItemsFactory.createRadioMenuItemForFileType(file, toggleGroup, appState.selectedFileTypeProperty()));
        }
        Menu pitTypesMenu = new Menu("XML PIT-11 rodzaje");

        ToggleGroup pityToggleGroup = new ToggleGroup();
        for (PitType type : PitType.values()) {
            pitTypesMenu.getItems().add(MenuItemsFactory.createRadioMenuItemForPitType(type, pityToggleGroup, appState.selectedPITTypeProperty()));
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
                    showWarningDialog("Zapis nie powiódł się", "Zapis do pliku nie zakończył się sukcesem");
                }
            }
        }
    }

    @FXML
    private void parsePersons() {
        if (appState.isFileTypeSelected()) {
            switch (appState.getSelectedFileType()) {
                case XML_ZUS: {
                    personCollection.getPersonsFromZUSXMLFile(fileCollection);
                }
                break;
                case XML_PIT_11: {
                    parsePITS();
                }
                break;
                default: {
                    showInfoDialog("Nie zaimplementowano", "W obecnej chwili funkcjonalonść nie jest zaimplementowana");
                }
            }
        } else {
            showInfoDialog("Nie wybrany typ plików", "Nie wybrano typu plików do parsowania. Otwórz menu 'typy plików' i wybierz tam odpowiednią opcję.");
        }
    }

    private void parsePITS() {
        if (appState.isPitFileSelected()) {
            switch (appState.getSelectedPITType()) {
                case PIT_11_2019: {
                    personCollection.getPersonsFromPITFiles(fileCollection);
                }
                break;
                default: {
                    showInfoDialog("Nie zaimplementowano", "W obecnej chwili funkcjonalonść nie jest zaimplementowana");
                }
            }
        } else {
            showInfoDialog("Nie wybrany typ pliku PIT", "Nie wybrano rodzaju plików PIT. Otwórz menu 'typy plików > XML PIT-11 rodzaje' i wybierz odpowiednią opcję.");
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

    @FXML
    private  void showAppInfo() {
        showInfoDialog("Opis aplikacji", "Prosta aplikacja służąca do parsowania plików XML wykorzystywanych do komunikacji z rożnymi urzędami. Sparsowane dane można zapisać jako pliki csv");
    }
}
