package io.github.brunolombardi.infra.mongodb.services.accounts;

import io.github.brunolombardi.core.protocols.accounts.Account;
import io.github.brunolombardi.core.protocols.accounts.AccountService;
import io.github.brunolombardi.infra.mongodb.mapping.AccountMapper;
import io.github.brunolombardi.infra.mongodb.repositories.MongoAccountRepository;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.Optional;

@Singleton
public class DbAccountService implements AccountService {

    @Inject
    private MongoAccountRepository mongoAccountRepository;

    @Override
    public Optional<Account> findByAccountBranchAndAccountNumber(String accountBranch, String accountNumber) {
        var found = mongoAccountRepository.findByAccountBranchAndAccountNumber(accountBranch, accountNumber);
        return found.map(AccountMapper::toAccount);
    }

    @Override
    public Account save(Account account) {
        var accountSaved = mongoAccountRepository.save(AccountMapper.fromAccount(account));
        return AccountMapper.toAccount(accountSaved);
    }
}
