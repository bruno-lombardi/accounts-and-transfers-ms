package io.github.brunolombardi.core.protocols.accounts;

import reactor.core.publisher.Mono;

public interface FindAccountUseCase {
    Mono<Account> findByAccountBranchAndAccountNumber(String accountBranch, String accountNumber);
}
