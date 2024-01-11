package ru.kuzds.grpc.client;

import com.google.protobuf.Int32Value;
import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.piomin.services.grpc.account.model.AccountProto;
import pl.piomin.services.grpc.account.model.AccountsServiceGrpc;

@Service
public class AccountClient {

    private static final Logger LOG = LoggerFactory.getLogger(AccountClient.class);

    @GrpcClient("local-grpc-server")
    AccountsServiceGrpc.AccountsServiceBlockingStub stub;

    public AccountProto.Accounts getAccountsByCustomerId(int customerId) {
        try {
            return stub.findByCustomer(Int32Value.newBuilder().setValue(customerId).build());
        } catch (final StatusRuntimeException e) {
            LOG.error("Error in communication", e);
            return null;
        }
    }
}
