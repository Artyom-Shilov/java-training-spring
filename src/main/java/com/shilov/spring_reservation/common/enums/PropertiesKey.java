package com.shilov.spring_reservation.common.enums;

public enum PropertiesKey {

    RESERVATION_STORAGE_PATH("reservationsStoragePath"),
    SPACE_STORAGE_PATH("spacesStoragePath"),
    GOODBYE_WRITER_CLASS_FILE_PATH("goodbyeWriterClassFilePath");

    private final String propertyName;

    PropertiesKey(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyName() {
        return propertyName;
    }
}
