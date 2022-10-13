package io.github.brunolombardi.infra.mongodb.repositories;

import io.github.brunolombardi.infra.mongodb.entities.AccountEntity;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.mongodb.annotation.MongoRepository;
import io.micronaut.data.repository.reactive.ReactiveStreamsCrudRepository;
import reactor.core.publisher.Mono;

@MongoRepository
public interface MongoAccountRepository extends ReactiveStreamsCrudRepository<AccountEntity, String> {
    Mono<AccountEntity> findByAccountBranchAndAccountNumber(@NonNull String accountBranch, @NonNull String accountNumber);
}
