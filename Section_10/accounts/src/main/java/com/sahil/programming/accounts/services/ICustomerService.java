package com.sahil.programming.accounts.services;

import com.sahil.programming.accounts.dtos.CustomerDetailsDto;

public interface ICustomerService {
    CustomerDetailsDto fetchCustomerDetails(String mobileNumber, String correlationId);
}
