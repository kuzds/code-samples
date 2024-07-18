package ru.kuzds.data.jpa.one_to_many_uni;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "departments")
@NoArgsConstructor
@Data
public class DepartmentOTMU {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "department_id")
    @ToString.Exclude
    private List<EmployeeOTMU> employees;

}

