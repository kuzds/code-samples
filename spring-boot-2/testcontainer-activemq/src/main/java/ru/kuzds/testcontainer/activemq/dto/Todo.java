package ru.kuzds.testcontainer.activemq.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Todo {

    private String id;
    private String description;
    private boolean completed;
}
