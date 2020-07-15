package io.marcinrg.factories;

import io.marcinrg.enums.FileTypesNames;
import io.marcinrg.enums.PitType;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;

public class MenuItemsFactory {

    public static RadioMenuItem createRadioMenuItemForFileType(FileTypesNames fileTypesNames, ToggleGroup toggleGroup,
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

    public static RadioMenuItem createRadioMenuItemForPitType(PitType pitType, ToggleGroup toggleGroup, SimpleObjectProperty<PitType> objectProperty) {
        RadioMenuItem radioMenuItem = new RadioMenuItem(pitType.toString());
        radioMenuItem.setToggleGroup(toggleGroup);
        radioMenuItem.setOnAction(event->{
            if (radioMenuItem.isSelected()) {
                objectProperty.set(pitType);
            }
        });
        return radioMenuItem;
    }

}
