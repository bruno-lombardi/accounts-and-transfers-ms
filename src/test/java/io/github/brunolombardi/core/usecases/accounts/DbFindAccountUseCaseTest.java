package io.github.brunolombardi.core.usecases.accounts;

import io.github.brunolombardi.core.protocols.accounts.AccountService;
import io.github.brunolombardi.test.mocks.AccountMock;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@MicronautTest(transactional = false)
class DbFindAccountUseCaseTest {
    @Inject
    private DbFindAccountUseCase findAccountUseCase;

    @Inject
    private AccountService accountService;

    @MockBean(AccountService.class)
    private AccountService accountServiceMock() {
        return mock(AccountService.class);
    }

    @Test
    void shouldReturnAccountAndCallAccountService() {
        doReturn(Mono.just(AccountMock.getAccountEntity()))
                .when(accountService)
                .findByAccountBranchAndAccountNumber(anyString(), anyString());
        var accountResponse = findAccountUseCase.findByAccountBranchAndAccountNumber("123", "123456");
        accountResponse.subscribe(Assertions::assertNotNull);
        verify(accountService, times(1))
                .findByAccountBranchAndAccountNumber(anyString(), anyString());
    }
}
