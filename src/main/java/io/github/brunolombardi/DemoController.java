package io.github.brunolombardi;

import io.github.brunolombardi.infra.mongodb.services.accounts.DbAccountService;
import io.github.brunolombardi.core.protocols.accounts.Account;
import io.github.brunolombardi.core.protocols.accounts.FindAccountUseCase;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Inject;

import java.math.BigDecimal;

@Controller("/")
public class DemoController {

    @Inject
    private FindAccountUseCase findAccountUseCase;

    @Inject
    private DbAccountService accountService;

    @Get(uri="/")
    public Account index() {
        return accountService.save(Account
                .builder()
                .balance(BigDecimal.valueOf(100.0))
                .holderTaxId("12312312312")
                .accountBranch("12312312")
                .accountNumber("123123")
                .build());
    }
}