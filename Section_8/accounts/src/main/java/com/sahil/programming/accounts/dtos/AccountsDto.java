package com.sahil.programming.accounts.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(
        name = "Accounts",
        description = "Schema to hold Account information"
)
public class AccountsDto {

    @NotEmpty(message = "Accounts Number cannot be empty")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Accounts Number must be 10 digits")
    @Schema(
            description = "Account Number of Account Microservices", example = "3454433243"
    )
    private Long accountNumber;

    @NotEmpty(message = "Account Type cannot be empty")
    @Schema(
            description = "Account type of Bank account", example = "Savings"
    )
    private String accountType;

    @NotEmpty(message = "Branch Address cannot be empty")
    @Schema(
            description = "Bank branch address", example = "123 NewYork"
    )
    private String branchAddress;
}
