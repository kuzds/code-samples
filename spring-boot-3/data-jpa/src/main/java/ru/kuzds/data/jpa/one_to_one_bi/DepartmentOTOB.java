package ru.kuzds.data.jpa.one_to_one_bi;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "departments")
@NoArgsConstructor
@Data
public class DepartmentOTOB {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "department")
    @ToString.Exclude
    private EmployeeOTOB employee;

}

