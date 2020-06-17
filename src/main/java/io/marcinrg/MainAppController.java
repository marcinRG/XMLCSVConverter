package io.marcinrg;

import io.marcinrg.collections.FileCollection;
import io.marcinrg.collections.PersonCollection;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.RadioMenuItem;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
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


    @FXML private Label labelSelectedFileType;
    @FXML private Label labelSelectedFileFormat;
    @FXML private RadioMenuItem XMLZusRadioItem;
    @FXML private RadioMenuItem XMLPITRadioItem;

    @FXML private RadioMenuItem PIT2017RadioItem;
    @FXML private RadioMenuItem PIT2018RadioItem;
    @FXML private RadioMenuItem PIT2019RadioItem;
    @FXML private RadioMenuItem PIT2020RadioItem;


    public MainAppController() {
    }

    @FXML
    private void initialize() {
        setDirectoryChooserOptions();
        setFileChooserOptions();
        XMLZusRadioItem.setSelected(true);
        XMLZusRadioItem.setText(labelZUSType);
        XMLPITRadioItem.setText(labelPITType);
        labelSelectedFileType.setText(labelType+labelZUSType);
        setEnabledFileFormatRadioItems("");
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
                new FileChooser.ExtensionFilter("svg", "*.svg")
        );
    }

    @FXML
    private void loadFiles(ActionEvent actionEvent) throws Exception {
        Stage stage = (Stage) ((Node) ((EventObject) actionEvent).getSource()).getScene().getWindow();
        File f = getFiles.showDialog(stage);
        if (f != null && f.exists() && f.isDirectory()) {
            fileCollection.getFilesFromDirectory(f);
        }
    }

    @FXML
    private void saveFiles(ActionEvent actionEvent) {
        if (personCollection.getPersonsList().size() > 0) {
            Stage stage = (Stage) ((Node) ((EventObject) actionEvent).getSource()).getScene().getWindow();
            File f = saveFile.showSaveDialog(stage);
//            if (f != null) {
//                boolean saved = FileSaver.saveToFile(f, personCollection.getPersonsList());
//            }
        }
    }

    @FXML
    private void changeFileType(ActionEvent event) {
        String txt = ((RadioMenuItem)event.getSource()).getText();
        labelSelectedFileType.setText(labelType+txt);
        setEnabledFileFormatRadioItems(txt);
    };

    @FXML
    private void changeFileFormat(ActionEvent event) {
        String txt = ((RadioMenuItem)event.getSource()).getText();
        labelSelectedFileFormat.setText(labelFormat+txt);
        setEnabledFileFormatRadioItems(txt);
    };

    @FXML
    private void parsePersons() {

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
