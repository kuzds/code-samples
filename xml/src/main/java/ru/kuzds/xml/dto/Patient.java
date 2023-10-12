package ru.kuzds.xml.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.Period;

@Data
public class Patient {

    public String firstName;
    public String middleName;
    public String lastName;
    public LocalDate birthday;
    public Gender gender;
    public String phone;

    public Patient(String firstName, String middleName, String lastName,
                   String birthday, String gender, String phone) {

        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.birthday = LocalDate.parse(birthday);

        try { this.gender = Gender.valueOf(gender); }
        catch (IllegalArgumentException e) { this.gender = Gender.undefined; }

        this.phone = phone;
    }

    public String name() {
        return String.format("%s %s %s", lastName, firstName, middleName );
    }

    public int age() {
        return Period.between(birthday, LocalDate.now()).getYears();
    }


}

