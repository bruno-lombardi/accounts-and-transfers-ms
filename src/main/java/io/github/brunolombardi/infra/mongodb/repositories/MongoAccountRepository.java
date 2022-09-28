package io.github.brunolombardi.infra.mongodb.repositories;

import io.github.brunolombardi.infra.mongodb.entities.AccountEntity;
import io.micronaut.data.mongodb.annotation.MongoRepository;
import io.micronaut.data.repository.CrudRepository;

import java.util.Optional;

@MongoRepository
public interface MongoAccountRepository extends CrudRepository<AccountEntity, String> {
    Optional<AccountEntity> findByAccountBranchAndAccountNumber(String accountBranch, String accountNumber);
}
