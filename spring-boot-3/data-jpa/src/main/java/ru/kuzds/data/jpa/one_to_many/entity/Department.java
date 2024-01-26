package ru.kuzds.data.jpa.one_to_many.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "departments")
@NoArgsConstructor
@Data
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "max_salary")
    private int maxSalary;

    @Column(name = "min_salary")
    private int minSalary;

    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "department",
            fetch = FetchType.LAZY
    )
    @ToString.Exclude
    private List<Employee> employees;

    public Department(String departmentName, int maxSalary, int minSalary) {
        this.name = departmentName;
        this.maxSalary = maxSalary;
        this.minSalary = minSalary;
    }

    public void addEmployee(Employee employee) {
        if (employees == null) {
            employees = new ArrayList<>();
        }

        employees.add(employee);
        employee.setDepartment(this); // !!!
    }
}

