package io.marcinrg.factories;

import io.marcinrg.enums.FileTypesNames;
import io.marcinrg.model.AppState;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;

public class MenuItemsFactory {

    public static RadioMenuItem createRadioMenuItemForFileOptionMenu(FileTypesNames fileTypesNames, ToggleGroup toggleGroup,
                                                                     SimpleObjectProperty<FileTypesNames> objectProperty) {
        RadioMenuItem radioMenuItem = new RadioMenuItem(fileTypesNames.getFileType());
        radioMenuItem.setToggleGroup(toggleGroup);

        radioMenuItem.setOnAction(event -> {
            if (radioMenuItem.isSelected()) {
                objectProperty.set(fileTypesNames);
            }
        });

        return radioMenuItem;
    }

}
