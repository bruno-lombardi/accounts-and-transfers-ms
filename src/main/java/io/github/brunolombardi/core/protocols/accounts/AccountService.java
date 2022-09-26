package io.github.brunolombardi.core.protocols.accounts;

import java.util.Optional;

public interface AccountService {
    Optional<Account> findByAccountBranchAndAccountNumber(String accountBranch, String accountNumber);
    Account save(Account account);
}
