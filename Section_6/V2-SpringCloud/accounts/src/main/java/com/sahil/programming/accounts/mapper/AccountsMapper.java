package com.sahil.programming.accounts.mapper;

import com.sahil.programming.accounts.Models.Accounts;
import com.sahil.programming.accounts.Models.Customer;
import com.sahil.programming.accounts.dtos.AccountsDto;
import com.sahil.programming.accounts.dtos.CustomerDto;

public class AccountsMapper {
    public static Accounts mapToAccounts(AccountsDto accountsDto,Accounts accounts) {
        accounts.setAccountNumber(accountsDto.getAccountNumber());
        accounts.setAccountType(accountsDto.getAccountType());
        accounts.setBranchAddress(accountsDto.getBranchAddress());
        return accounts;
    }

    public static AccountsDto mapToAccountsDto(Accounts accounts, AccountsDto accountsDto) {
        accountsDto.setAccountNumber(accounts.getAccountNumber());
        accountsDto.setAccountType(accounts.getAccountType());
        accountsDto.setBranchAddress(accounts.getBranchAddress());
        return accountsDto;
    }


}
