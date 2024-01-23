package ru.kuzds.grpc.server;


import org.springframework.stereotype.Repository;
import pl.piomin.services.grpc.account.model.AccountProto;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccountRepository {

    List<AccountProto.Account> accounts;
    AtomicInteger id = new AtomicInteger();

    @PostConstruct
    public void init() {
        accounts = new ArrayList<>();
        accounts.add(AccountProto.Account.newBuilder().setId(1).setCustomerId(1).setNumber("111111").build());
        accounts.add(AccountProto.Account.newBuilder().setId(2).setCustomerId(2).setNumber("222222").build());
        accounts.add(AccountProto.Account.newBuilder().setId(3).setCustomerId(3).setNumber("333333").build());
        accounts.add(AccountProto.Account.newBuilder().setId(4).setCustomerId(4).setNumber("444444").build());
        accounts.add(AccountProto.Account.newBuilder().setId(5).setCustomerId(1).setNumber("555555").build());
        accounts.add(AccountProto.Account.newBuilder().setId(6).setCustomerId(2).setNumber("666666").build());
        accounts.add(AccountProto.Account.newBuilder().setId(7).setCustomerId(2).setNumber("777777").build());

        id.set(accounts.size());
    }

    public List<AccountProto.Account> findAll() {
        return accounts;
    }

    public List<AccountProto.Account> findByCustomer(int customerId) {
        return accounts.stream().filter(it -> it.getCustomerId() == customerId).toList();
    }

    public AccountProto.Account findByNumber(String number) {
        return accounts.stream()
                .filter(it -> it.getNumber().equals(number))
                .findFirst()
                .orElseThrow();
    }

    public AccountProto.Account add(int customerId, String number) {
        return AccountProto.Account.newBuilder()
                .setId(id.incrementAndGet())
                .setCustomerId(customerId)
                .setNumber(number)
                .build();
    }
}
