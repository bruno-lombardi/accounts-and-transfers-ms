package io.github.brunolombardi.infra.mongodb.services.accounts;

import io.github.brunolombardi.core.protocols.accounts.Account;
import io.github.brunolombardi.core.protocols.accounts.AccountService;
import io.github.brunolombardi.infra.mongodb.entities.AccountEntity;
import io.github.brunolombardi.infra.mongodb.mapping.AccountMapper;
import io.github.brunolombardi.infra.mongodb.repositories.MongoAccountRepository;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Singleton
public class DbAccountService implements AccountService {

    @Inject
    private MongoAccountRepository mongoAccountRepository;

    @Override
    public Mono<Account> findByAccountBranchAndAccountNumber(String accountBranch, String accountNumber) {
        var found = mongoAccountRepository.findByAccountBranchAndAccountNumber(accountBranch, accountNumber);
        return found.map(AccountMapper::toAccount);
    }

    @Override
    public Mono<Account> save(Account account) {
        var accountEntity = AccountMapper.fromAccount(account);
        if (accountEntity.getId() == null) {
            var response = (Flux<AccountEntity>) mongoAccountRepository.save(accountEntity);
            return response.map(AccountMapper::toAccount).next();
        }
        var response = (Flux<AccountEntity>) mongoAccountRepository.update(accountEntity);
        return response.map(AccountMapper::toAccount).next();
    }
}
