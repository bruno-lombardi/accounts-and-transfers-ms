package io.github.brunolombardi.core.protocols.transactions;

public interface MakeTransactionUseCase {
    TransactionResultDTO makeTransaction(MakeTransactionDTO makeTransactionDTO);
}
