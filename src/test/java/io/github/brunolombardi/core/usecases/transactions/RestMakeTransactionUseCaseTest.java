package io.github.brunolombardi.core.usecases.transactions;

import io.github.brunolombardi.core.protocols.accounts.Account;
import io.github.brunolombardi.core.protocols.accounts.AccountNotFoundException;
import io.github.brunolombardi.core.protocols.accounts.AccountService;
import io.github.brunolombardi.core.protocols.transactions.AccountTransaction;
import io.github.brunolombardi.core.protocols.transactions.AccountTransactionService;
import io.github.brunolombardi.core.protocols.transactions.InsufficientBalanceException;
import io.github.brunolombardi.core.protocols.transactions.MakeTransactionErrorException;
import io.github.brunolombardi.test.mocks.AccountMock;
import io.github.brunolombardi.test.mocks.AccountTransactionMock;
import io.github.brunolombardi.test.mocks.MakeTransactionUseCaseMock;
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
import static org.mockito.Mockito.*;

@MicronautTest(transactional = false)
class RestMakeTransactionUseCaseTest {

    @Inject
    private RestMakeTransactionUseCase restMakeTransactionUseCase;

    @Inject
    private AccountService accountService;

    @MockBean(AccountService.class)
    private AccountService accountServiceMock() {
        return mock(AccountService.class);
    }

    @Inject
    private AccountTransactionService accountTransactionService;

    @MockBean(AccountTransactionService.class)
    private AccountTransactionService accountTransactionServiceMock() {
        return mock(AccountTransactionService.class);
    }

    @Test
    void shouldMakeAccountTransactionSuccessful() {
        var accountTransactionMock = AccountTransactionMock.getAccountTransaction();
        when(accountService.findByAccountBranchAndAccountNumber(anyString(), anyString()))
            .thenReturn(Mono.just(AccountMock.getAccountWithId("123")))
            .thenReturn(Mono.just(AccountMock.getAccountWithId("321")));
        when(accountService.save(any(Account.class)))
                .thenReturn(Mono.just(AccountMock.getAccount()));

        doReturn(Flux.just(accountTransactionMock))
                .when(accountTransactionService).save(any(AccountTransaction.class));
        var makeTransactionOptions = MakeTransactionUseCaseMock.getMakeTransactionOptions();
        var makeTransactionResponse = restMakeTransactionUseCase.makeTransaction(
            makeTransactionOptions
        );
        StepVerifier.create(makeTransactionResponse)
            .assertNext((transactionResult) -> {
                assertNotNull(transactionResult);
                assertEquals(transactionResult.getTransactionId(), accountTransactionMock.getTransactionId());
                assertEquals(transactionResult.getAmount(), makeTransactionOptions.getAmount());

                verify(accountService, times(2))
                        .findByAccountBranchAndAccountNumber(anyString(), anyString());
                verify(accountService, times(2))
                        .save(any(Account.class));
                verify(accountTransactionService, times(1))
                        .save(any(AccountTransaction.class));
            })
            .verifyComplete();
    }

    @Test
    void shouldThrowWhenInsufficientBalance() {
        var accountTransactionMock = AccountTransactionMock.getAccountTransaction();
        var zeroedBalanceAccount = AccountMock.getAccount();
        zeroedBalanceAccount.setBalance(BigDecimal.ZERO);
        when(accountService.findByAccountBranchAndAccountNumber(anyString(), anyString()))
            .thenReturn(Mono.just(zeroedBalanceAccount))
            .thenReturn(Mono.just(AccountMock.getAccountWithId("321")));

        doReturn(Flux.just(accountTransactionMock))
                .when(accountTransactionService).save(any(AccountTransaction.class));
        var makeTransactionOptions = MakeTransactionUseCaseMock.getMakeTransactionOptions();
        var makeTransactionResponse = restMakeTransactionUseCase.makeTransaction(
            makeTransactionOptions
        );
        StepVerifier.create(makeTransactionResponse)
            .expectError(InsufficientBalanceException.class)
            .verify();
    }

    @Test
    void shouldThrowWhenOriginAndDestinationAccountsAreEqual() {
        var accountTransactionMock = AccountTransactionMock.getAccountTransaction();
        var accountMock = AccountMock.getAccount();
        when(accountService.findByAccountBranchAndAccountNumber(anyString(), anyString()))
            .thenReturn(Mono.just(accountMock))
            .thenReturn(Mono.just(accountMock));

        var makeTransactionOptions = MakeTransactionUseCaseMock.getMakeTransactionOptions();
        var makeTransactionResponse = restMakeTransactionUseCase.makeTransaction(
            makeTransactionOptions
        );
        StepVerifier.create(makeTransactionResponse)
            .expectError(MakeTransactionErrorException.class)
            .verify();
    }

    @Test
    void shouldThrowWhenAccountIsNotFound() {
        when(accountService.findByAccountBranchAndAccountNumber(anyString(), anyString()))
            .thenReturn(Mono.empty());

        var makeTransactionOptions = MakeTransactionUseCaseMock.getMakeTransactionOptions();
        var makeTransactionResponse = restMakeTransactionUseCase.makeTransaction(
            makeTransactionOptions
        );
        StepVerifier.create(makeTransactionResponse)
            .expectError(AccountNotFoundException.class)
            .verify();
    }

}
