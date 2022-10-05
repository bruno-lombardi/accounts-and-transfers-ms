package io.github.brunolombardi.test.clients;

import io.github.brunolombardi.core.protocols.accounts.Account;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.client.annotation.Client;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Client("/accounts")
public interface AccountsClient {

    @Get("/{branch}/{accountNumber}")
    Optional<Account> findAccount(
            @PathVariable("branch") @NotNull @NotBlank String branch,
            @PathVariable("accountNumber") @NotNull @NotBlank String accountNumber
    );
}
