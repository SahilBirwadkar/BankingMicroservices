package com.sahil.programming.accounts.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@Schema
public class CustomerDetailsDto {
    private String name;
    private String email;
    private String mobileNumber;
    private AccountsDto accountsDto;
    private LoansDto loansDto;
    private CardDto cardsDto;
}
