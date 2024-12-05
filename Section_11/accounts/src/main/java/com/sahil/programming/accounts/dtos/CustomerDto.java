package com.sahil.programming.accounts.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(
        name="Customer",
        description = "Schema to hold Customer Details"
)
public class CustomerDto {
    @NotEmpty (message = "Name cannot be empty")
    @Size(min = 5, max = 30, message = "Name must be between 5 and 30 characters")
    @Schema(
            description = "Customer Name",
            example = "Sahil Birwadkar"
    )
    private String name;

    @NotEmpty (message = "Email cannot be empty")
    @Email (message = "Email must be valid")
    @Schema(
        description = "Email address of Customer",
        example = "sahil@gmail.com"
    )
    private String email;

    @Pattern(regexp = "(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
    @Schema(
            description = "Mobile number of Customer",
            example = "91111111111"
    )
    private String mobileNumber;

    @Schema(
            description = "Account details of the Customer"
    )
    private AccountsDto accountsDto;
}
