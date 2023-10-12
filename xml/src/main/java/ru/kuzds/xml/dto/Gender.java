package ru.kuzds.xml.dto;

public enum Gender {
    undefined("Н/О"),
    male("М"),
    female("Ж");

    private final String alias;

    Gender(String alias) {
        this.alias = alias;
    }

    public String alias() {
        return alias;
    }
}
