package io.github.brunolombardi.infra.mongodb.repositories;

import io.github.brunolombardi.infra.mongodb.entities.AccountTransactionEntity;
import io.micronaut.data.mongodb.annotation.MongoRepository;
import io.micronaut.data.repository.CrudRepository;

@MongoRepository
public interface MongoAccountTransactionRepository extends CrudRepository<AccountTransactionEntity, String>  {
}
