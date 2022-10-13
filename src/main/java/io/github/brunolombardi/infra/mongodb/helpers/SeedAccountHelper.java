package io.github.brunolombardi.infra.mongodb.helpers;

import io.github.brunolombardi.infra.mongodb.entities.AccountEntity;
import io.github.brunolombardi.infra.mongodb.repositories.MongoAccountRepository;
import io.micronaut.context.annotation.Requires;
import io.micronaut.runtime.event.annotation.EventListener;
import io.micronaut.runtime.server.event.ServerStartupEvent;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.ArrayList;

@Singleton
@Requires(notEnv="test")
public class SeedAccountHelper {

    @Inject
    private MongoAccountRepository mongoAccountRepository;

    private static final Logger LOG = LoggerFactory.getLogger(SeedAccountHelper.class);

    @EventListener
    void onStartup(final ServerStartupEvent event) {
        var accounts = new ArrayList<AccountEntity>();
        var accountOne = mongoAccountRepository.findByAccountBranchAndAccountNumber("1", "123456").blockOptional();
        var accountTwo = mongoAccountRepository.findByAccountBranchAndAccountNumber("1", "654321").blockOptional();
        if (accountOne.isPresent() && accountTwo.isPresent()) {
            return;
        }
        accounts.add(AccountEntity
                .builder()
                .accountBranch("1")
                .accountNumber("123456")
                .holderTaxId("55641982040")
                .balance(BigDecimal.valueOf(100.0))
                .build());
        accounts.add(AccountEntity
                .builder()
                .accountBranch("1")
                .accountNumber("654321")
                .holderTaxId("07675682068")
                .balance(BigDecimal.valueOf(50.0))
                .build());

        var deleteAllAccounts = (Mono<Long>) mongoAccountRepository.deleteAll();
        deleteAllAccounts.subscribe((affected) -> {
            var saveAllAccounts = (Flux<AccountEntity>) mongoAccountRepository.saveAll(accounts);
            saveAllAccounts.blockLast();
            LOG.info("Clean accounts and seeded new accounts.");
        });
    }
}
