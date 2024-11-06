package com.sahil.programming.accounts.services;

import com.sahil.programming.accounts.Exception.CustomerAlreadyExistsException;
import com.sahil.programming.accounts.dtos.CustomerDto;

public interface IAccountServices {
    void createAccount(CustomerDto customerDto);
    CustomerDto fetchAccount(String mobileNumber);

    boolean updateAccount(CustomerDto customerDto);

    boolean deleteAccount(String mobileNumber);
}
