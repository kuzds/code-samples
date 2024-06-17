package ru.kuzds.reactor.rabbitmq.protocol.helper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProtocolExchange {
    public static final String SUCCESS_RESPONSE = "Protocol.CommandResult.Success";
    public static final String FAILED_RESPONSE = "Protocol.CommandResult.Failed";
    public static final String REJECT = "Protocol.Reject";
    public static final String COMMAND = "Protocol.Command";
}
