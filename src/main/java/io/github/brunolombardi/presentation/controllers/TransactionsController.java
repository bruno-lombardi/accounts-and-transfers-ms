package io.github.brunolombardi.presentation.controllers;

import io.github.brunolombardi.core.protocols.transactions.MakeTransactionOptions;
import io.github.brunolombardi.core.protocols.transactions.MakeTransactionUseCase;
import io.github.brunolombardi.core.protocols.transactions.TransactionResult;
import io.github.brunolombardi.infra.mongodb.entities.AccountTransactionEntity;
import io.github.brunolombardi.infra.mongodb.repositories.MongoAccountTransactionRepository;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Controller("/transactions")
@ExecuteOn(TaskExecutors.IO)
public class TransactionsController {
    @Inject
    private MakeTransactionUseCase makeTransactionUseCase;
    @Inject
    private MongoAccountTransactionRepository mongoAccountTransactionRepository;

    private static final Logger LOG = LoggerFactory.getLogger(TransactionsController.class);

    @Post
    @Status(HttpStatus.CREATED)
    public Mono<TransactionResult> makeTransaction(@NotNull @Valid @Body MakeTransactionOptions makeTransactionOptions) {
        return makeTransactionUseCase.makeTransaction(makeTransactionOptions);
    }

    @Get
    public Flux<AccountTransactionEntity> list() {
        return (Flux<AccountTransactionEntity>) mongoAccountTransactionRepository.findAll();
    }
}
