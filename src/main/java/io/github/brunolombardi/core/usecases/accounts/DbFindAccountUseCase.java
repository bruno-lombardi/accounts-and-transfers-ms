package io.github.brunolombardi.core.usecases.accounts;

import io.github.brunolombardi.core.protocols.accounts.Account;
import io.github.brunolombardi.core.protocols.accounts.AccountService;
import io.github.brunolombardi.core.protocols.accounts.FindAccountUseCase;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import reactor.core.publisher.Mono;

@Singleton
public class DbFindAccountUseCase implements FindAccountUseCase {

    @Inject
    private AccountService accountService;

    @Override
    public Mono<Account> findByAccountBranchAndAccountNumber(String accountBranch, String accountNumber) {
        return accountService.findByAccountBranchAndAccountNumber(accountBranch, accountNumber);
    }
}
