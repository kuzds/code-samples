package ru.kuzds.dto.ci45;

public enum AddressTypeCode {
    ADDR,
    PBOX,
    HOME,
    BIZZ,
    MLTO,
    DLVY;


    public String value() {
        return this.name();
    }
}
