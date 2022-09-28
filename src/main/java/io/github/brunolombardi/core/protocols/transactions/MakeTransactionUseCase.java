package io.github.brunolombardi.core.protocols.transactions;

public interface MakeTransactionUseCase {
    TransactionResult makeTransaction(MakeTransactionOptions makeTransactionOptions);
}
