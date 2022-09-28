package io.github.brunolombardi.infra.mongodb.services.accounts;

import io.github.brunolombardi.core.protocols.accounts.Account;
import io.github.brunolombardi.infra.mongodb.entities.AccountEntity;
import io.github.brunolombardi.infra.mongodb.repositories.MongoAccountRepository;
import io.github.brunolombardi.infra.mongodb.services.accounts.DbAccountService;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@MicronautTest
public class DbAccountServiceTest {

    @Inject
    private DbAccountService accountService;

    @Inject
    private MongoAccountRepository mongoAccountRepository;

    @MockBean(MongoAccountRepository.class)
    private MongoAccountRepository mongoAccountRepositoryMock() {
        return mock(MongoAccountRepository.class);
    }

    @Test
    public void shouldFindAccountByAccountBranchAndAccountNumberIfItExists() {
        var account = AccountEntity
                .builder()
                .id("123123121231")
                .accountBranch("123")
                .accountNumber("123")
                .balance(BigDecimal.valueOf(100.0))
                .holderTaxId("12312312312")
                .build();
        when(mongoAccountRepository.findByAccountBranchAndAccountNumber(anyString(), anyString()))
                .thenReturn(Optional.of(account));
        var foundAccount = accountService.findByAccountBranchAndAccountNumber("123", "123");
        assertTrue(foundAccount.isPresent());
        assertEquals(foundAccount.get().getId(), account.getId());
        assertEquals(foundAccount.get().getAccountBranch(), account.getAccountBranch());
        assertEquals(foundAccount.get().getAccountNumber(), account.getAccountNumber());
        assertEquals(foundAccount.get().getHolderTaxId(), account.getHolderTaxId());

        verify(mongoAccountRepository, times(1))
                .findByAccountBranchAndAccountNumber(anyString(), anyString());
    }

    @Test
    public void shouldNotFindAccountByAccountBranchAndAccountNumberIfItDoesNotExists() {
        when(mongoAccountRepository.findByAccountBranchAndAccountNumber(anyString(), anyString()))
                .thenReturn(Optional.empty());
        var foundAccount = accountService.findByAccountBranchAndAccountNumber("123", "123");
        assertTrue(foundAccount.isEmpty());

        verify(mongoAccountRepository, times(1))
                .findByAccountBranchAndAccountNumber(anyString(), anyString());
    }

    @Test
    public void shouldSaveAccount() {
        when(mongoAccountRepository.save(any(AccountEntity.class)))
                .thenReturn(mock(AccountEntity.class));
        var accountToSave = Account
                .builder()
                .accountBranch("123")
                .accountNumber("123")
                .balance(BigDecimal.valueOf(100.0))
                .holderTaxId("12312312312")
                .build();
        accountService.save(accountToSave);

        verify(mongoAccountRepository, times(1))
                .save(any(AccountEntity.class));
    }
}
