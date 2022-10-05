package io.github.brunolombardi.presentation.controllers;

import io.github.brunolombardi.core.protocols.accounts.Account;
import io.github.brunolombardi.core.protocols.accounts.FindAccountUseCase;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Controller("/accounts")
@ExecuteOn(TaskExecutors.IO)
public class AccountsController {

    @Inject
    private FindAccountUseCase findAccountUseCase;

    private static final Logger LOG = LoggerFactory.getLogger(AccountsController.class);

    @Get("/{branch}/{accountNumber}")
    public Optional<Account> findAccount(
            @PathVariable("branch") @NotNull @NotBlank String branch,
            @PathVariable("accountNumber") @NotNull @NotBlank String accountNumber
    ) {
        LOG.info("Searching account by branch {} and account number {}", branch, accountNumber);
        return findAccountUseCase.findByAccountBranchAndAccountNumber(
                branch,
                accountNumber
        );
    }
}
