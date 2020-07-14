package io.marcinrg.enums;

public enum FileTypesNames {
    XML_ZUS("XML ZUS DRA", "plik xml zus dra",0),
    XML_PIT_11("XML PIT-11", "plik xml urząd skarbowy pit-11",1);

    private FileTypesNames(String fileType, String description, int value) {
        this.fileType = fileType;
        this.description = description;
        this.value =value;
    }

    public String getFileType() {
        return this.fileType;
    }

    private final String fileType;
    private final int value;
    private final String description;

}
