package io.github.brunolombardi.presentation.controllers;

import io.github.brunolombardi.core.protocols.accounts.AccountService;
import io.github.brunolombardi.infra.mongodb.entities.AccountEntity;
import io.github.brunolombardi.infra.mongodb.repositories.MongoAccountRepository;
import io.github.brunolombardi.test.clients.AccountsClient;
import io.github.brunolombardi.test.mocks.AccountMock;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@MicronautTest
class AccountsControllerTest {

    @Inject
    private AccountsClient accountsClient;

    @Inject
    private MongoAccountRepository mongoAccountRepository;

    @MockBean(MongoAccountRepository.class)
    private MongoAccountRepository mongoAccountRepositoryMock() {
        return mock(MongoAccountRepository.class);
    }

    @Test
    void shouldReturnEmptyIfAccountDoesNotExist() {
        doReturn(Optional.empty())
                .when(mongoAccountRepository).findByAccountBranchAndAccountNumber(anyString(), anyString());
        var accountResponse = accountsClient.findAccount("123", "123456");
        assertFalse(accountResponse.isPresent());
    }

    @Test
    void shouldReturnAccount() {
        AccountEntity accountMock = AccountMock.getAccountEntity();
        doReturn(Optional.of(accountMock))
                .when(mongoAccountRepository).findByAccountBranchAndAccountNumber(anyString(), anyString());
        var accountResponse = accountsClient.findAccount("123", "123456");
        assertTrue(accountResponse.isPresent());
        var account = accountResponse.get();

        assertEquals(
                accountMock.getId(),
                account.getId()
        );
        assertEquals(
                accountMock.getAccountBranch(),
                account.getAccountBranch()
        );
        assertEquals(
                accountMock.getAccountNumber(),
                account.getAccountNumber()
        );
    }

}
