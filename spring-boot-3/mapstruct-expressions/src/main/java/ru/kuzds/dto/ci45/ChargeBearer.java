package ru.kuzds.dto.ci45;

public enum ChargeBearer {
    DEBT,
    CRED,
    SHAR,
    SLEV;

    public String value() {
        return this.name();
    }
}
