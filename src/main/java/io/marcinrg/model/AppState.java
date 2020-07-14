package io.marcinrg.model;

import io.marcinrg.enums.FileTypesNames;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;


public class AppState {
    private SimpleStringProperty appName = new SimpleStringProperty("Conwerter XML do CSV");
    private SimpleObjectProperty<FileTypesNames> selectedFileType = new SimpleObjectProperty<>();
    private SimpleBooleanProperty pitFileDisabled = new SimpleBooleanProperty(false);

    public AppState() {
        pitFileDisabled.set(true);
        selectedFileTypeProperty().addListener((observableValue, oldValue, newValue) -> {
            if ((newValue != null) && (newValue.equals(FileTypesNames.XML_PIT_11))) {
                pitFileDisabled.set(false);
            } else {
                pitFileDisabled.set(true);
            }
        });
    }

    public boolean getPitFileDisabled() {
        return pitFileDisabled.get();
    }

    public SimpleBooleanProperty pitFileDisabledProperty() {
        return pitFileDisabled;
    }

    public void setPitFileDisabled(boolean pitFileDisabled) {
        this.pitFileDisabled.set(pitFileDisabled);
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
