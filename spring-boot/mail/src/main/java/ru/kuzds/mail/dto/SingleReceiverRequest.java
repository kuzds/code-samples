package ru.kuzds.mail.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SingleReceiverRequest {

    @Schema(description = "Почтовый адрес")
    private String receiver;
}
