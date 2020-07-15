package io.marcinrg.model;

import io.marcinrg.enums.FileTypesNames;
import io.marcinrg.enums.PitType;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;


public class AppState {
    private SimpleStringProperty appName = new SimpleStringProperty("Conwerter XML do CSV");
    private SimpleObjectProperty<FileTypesNames> selectedFileType = new SimpleObjectProperty<>();
    private SimpleObjectProperty<PitType> selectedPITType = new SimpleObjectProperty<>();
    private SimpleBooleanProperty pitFileDisabled = new SimpleBooleanProperty(false);
    private SimpleBooleanProperty fileTypeSelected = new SimpleBooleanProperty(false);
    private SimpleBooleanProperty pitFileSelected = new SimpleBooleanProperty(false);

    public AppState() {
        pitFileDisabled.set(true);
        selectedFileTypeProperty().addListener((observableValue, oldValue, newValue) -> {
            if ((newValue != null) && (newValue.equals(FileTypesNames.XML_PIT_11))) {
                pitFileDisabled.set(false);
                pitFileSelected.set(false);
            } else {
                pitFileDisabled.set(true);
            }
            if (newValue !=null) {
                fileTypeSelected.set(true);
            }
        });

        selectedPITTypeProperty().addListener((observableValue, oldValue, newValue)->{
            if (newValue !=null) {
                pitFileSelected.set(true);
            }
        });

    }

    public boolean isPitFileSelected() {
        return pitFileSelected.get();
    }

    public boolean isFileTypeSelected() {
        return fileTypeSelected.get();
    }

    public SimpleBooleanProperty pitFileDisabledProperty() {
        return pitFileDisabled;
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

    public PitType getSelectedPITType() {
        return selectedPITType.get();
    }

    public SimpleObjectProperty<PitType> selectedPITTypeProperty() {
        return selectedPITType;
    }

    public String getAppTitle() {
        return this.appName.get();
    }
}
