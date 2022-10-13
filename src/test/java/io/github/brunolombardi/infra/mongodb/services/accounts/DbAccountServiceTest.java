package io.github.brunolombardi.infra.mongodb.services.accounts;

import io.github.brunolombardi.core.protocols.accounts.Account;
import io.github.brunolombardi.infra.mongodb.entities.AccountEntity;
import io.github.brunolombardi.infra.mongodb.repositories.MongoAccountRepository;
import io.github.brunolombardi.infra.mongodb.repositories.MongoAccountTransactionRepository;
import io.github.brunolombardi.test.mocks.AccountMock;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@MicronautTest(transactional = false)
class DbAccountServiceTest {

    @Inject
    private DbAccountService accountService;

    @Inject
    private MongoAccountRepository mongoAccountRepository;

    @MockBean(MongoAccountRepository.class)
    private MongoAccountRepository mongoAccountRepositoryMock() {
        return mock(MongoAccountRepository.class);
    }

    @Test
    void shouldFindAccountByAccountBranchAndAccountNumberIfItExists() {
        var accountMock = AccountMock.getAccountEntity();
        doReturn(Mono.just(accountMock))
                .when(mongoAccountRepository).findByAccountBranchAndAccountNumber(anyString(), anyString());

        var foundAccount = accountService.findByAccountBranchAndAccountNumber("123", "123");
        StepVerifier.create(foundAccount)
            .assertNext((account) -> {
                assertEquals(account.getId(), accountMock.getId());
                assertEquals(account.getAccountBranch(), accountMock.getAccountBranch());
                assertEquals(account.getAccountNumber(), accountMock.getAccountNumber());
                assertEquals(account.getHolderTaxId(), accountMock.getHolderTaxId());
            })
            .verifyComplete();
    }



    @Test
    void shouldNotFindAccountByAccountBranchAndAccountNumberIfItDoesNotExists() {
        doReturn(Mono.empty())
                .when(mongoAccountRepository).findByAccountBranchAndAccountNumber(anyString(), anyString());
        var foundAccount = accountService.findByAccountBranchAndAccountNumber("123", "123");
        foundAccount.subscribe(Assertions::assertNull);
    }

    @Test
    void shouldSaveAccount() {
        doReturn(Flux.just(mock(AccountEntity.class)))
                .when(mongoAccountRepository).save(any(AccountEntity.class));
        var accountToSave = Account
                .builder()
                .accountBranch("123")
                .accountNumber("123")
                .balance(BigDecimal.valueOf(100.0))
                .holderTaxId("12312312312")
                .build();
        var savedAccount = accountService.save(accountToSave);
        savedAccount.subscribe(Assertions::assertNotNull);
    }
}
