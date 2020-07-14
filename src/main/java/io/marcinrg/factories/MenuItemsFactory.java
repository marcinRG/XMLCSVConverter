package io.marcinrg.factories;

import io.marcinrg.enums.FileTypesNames;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.RadioMenuItem;

public class MenuItemsFactory {

    public static RadioMenuItem createRadioMenuItemForFileOptionMenu(FileTypesNames fileTypesNames, SimpleObjectProperty<FileTypesNames> objectProperty) {
        RadioMenuItem radioMenuItem = new RadioMenuItem(fileTypesNames.getFileType());
        radioMenuItem.setOnAction(event -> {
            objectProperty.set(fileTypesNames);
            System.out.println(objectProperty.get().getFileType());
        });
        return radioMenuItem;
    }

}
