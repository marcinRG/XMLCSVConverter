package io.marcinrg.model;

import io.marcinrg.enums.FileTypesNames;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;


public class AppState {
    private SimpleStringProperty appName = new SimpleStringProperty("Conwerter XML do CSV");
    private SimpleObjectProperty<FileTypesNames> selectedFileType = new SimpleObjectProperty<>();

    public AppState() {
    }

    public FileTypesNames getSelectedFileType() {
        return selectedFileType.get();
    }

    public SimpleObjectProperty<FileTypesNames> selectedFileTypeProperty() {
        return selectedFileType;
    }

    public void setSelectedFileType(FileTypesNames selectedFileType) {
        this.selectedFileType.set(selectedFileType);
    }

    public String getAppTitle() {
        return this.appName.get();
    }
}
