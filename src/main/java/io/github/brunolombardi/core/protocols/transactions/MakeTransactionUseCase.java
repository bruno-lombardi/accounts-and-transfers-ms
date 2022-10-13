package io.github.brunolombardi.core.protocols.transactions;

import reactor.core.publisher.Mono;

public interface MakeTransactionUseCase {
    Mono<TransactionResult> makeTransaction(MakeTransactionOptions makeTransactionOptions);
}
