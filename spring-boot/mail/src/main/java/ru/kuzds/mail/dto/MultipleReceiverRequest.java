package ru.kuzds.mail.dto;

import lombok.Data;

import java.util.List;

@Data
public class MultipleReceiverRequest {

    private List<String> receivers;
    private String copy;
    private String hiddenCopy;
}
