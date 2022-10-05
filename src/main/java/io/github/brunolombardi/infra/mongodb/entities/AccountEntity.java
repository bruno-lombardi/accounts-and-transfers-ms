package io.github.brunolombardi.infra.mongodb.entities;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@AllArgsConstructor
@Data
@Builder
@MappedEntity
public class AccountEntity {

    @Id
    @GeneratedValue
    private String id;

    @NonNull
    @NotBlank
    private String accountBranch;

    @NonNull
    @NotBlank
    private String accountNumber;

    @NonNull
    @NotBlank
    private String holderTaxId;

    @NonNull
    private BigDecimal balance;

}
