package io.github.brunolombardi.core.protocols.transactions;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountTransactionService {
    Flux<AccountTransaction> save(AccountTransaction accountTransaction);
    Mono<Void> publishAccountTransactionCreatedEvent(AccountTransactionCreatedEvent transactionCreatedEvent);
}
