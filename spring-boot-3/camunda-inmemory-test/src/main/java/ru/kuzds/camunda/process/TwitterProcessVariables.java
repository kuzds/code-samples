package ru.kuzds.camunda.process;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TwitterProcessVariables {

    private String tweet;
    private String author;
    private String boss;
    private boolean approved;
}
