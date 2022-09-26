package io.github.brunolombardi;

import io.github.brunolombardi.core.protocols.accounts.FindAccountUseCase;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;

@Controller("/")
public class DemoController {

    @Inject
    private FindAccountUseCase findAccountUseCase;

    @Get(uri="/", produces="text/plain")
    public String index() {
        return "Example Response";
    }
}