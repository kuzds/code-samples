package ru.kuzds.dto.ci45;

public enum Settlement {
    INDA,
    INGA,
    COVE,
    CLRG;

    public String value() {
        return this.name();
    }
}
