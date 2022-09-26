package io.github.brunolombardi.core.protocols.accounts;

import java.util.Optional;

public interface FindAccountUseCase {
    Optional<Account> findByAccountBranchAndAccountNumber(String accountBranch, String accountNumber);
}
