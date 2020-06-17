package io.marcinrg;

import io.marcinrg.collections.FileCollection;
import io.marcinrg.collections.PersonCollection;
import io.marcinrg.utils.FileSaver;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EventObject;

public class MainAppController {
    private DirectoryChooser getFiles = new DirectoryChooser();
    private FileChooser saveFile = new FileChooser();

    private FileCollection fileCollection = new FileCollection();
    private PersonCollection personCollection = new PersonCollection();

    public MainAppController() {
        getFiles.setTitle("Otwórz katalog z plikami .xml do przetworzenia");
        getFiles.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );

        saveFile.setTitle("Zapisz osoby");
        saveFile.setInitialDirectory(new File(System.getProperty("user.home")));
        saveFile.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("wszystkie pliki", "*.*"),
                new FileChooser.ExtensionFilter("svg", "*.svg")
        );
    }


    @FXML
    private void showMessage() throws IOException {
        System.out.println("Button pressed");
    }

    @FXML
    private void loadFiles(ActionEvent actionEvent) throws Exception {
        Stage stage = (Stage) ((Node) ((EventObject) actionEvent).getSource()).getScene().getWindow();
        System.out.println(stage.getTitle());
        File f = getFiles.showDialog(stage);
        if (f != null && f.exists() && f.isDirectory()) {
            fileCollection.getFilesFromDirectory(f);
            ArrayList<File> files = fileCollection.getFileList();
            for (File file : files) {
                System.out.println(file.getName());
                System.out.println(file.getAbsolutePath());
            }
            System.out.println("file collection size:");
            System.out.println(fileCollection.getFileList().size());
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
    void clearPersons() {
        personCollection.clear();
    }

    @FXML
    private void clearFiles() {
        fileCollection.clear();
        System.out.println("file collection size:");
        System.out.println(fileCollection.getFileList().size());
    }

    @FXML
    private void closeApplication() {
        System.out.println("close app");
    }

}
