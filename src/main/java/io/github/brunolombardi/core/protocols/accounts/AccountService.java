package io.github.brunolombardi.core.protocols.accounts;

import reactor.core.publisher.Mono;

public interface AccountService {
    Mono<Account> findByAccountBranchAndAccountNumber(String accountBranch, String accountNumber);
    Mono<Account> save(Account account);
}
