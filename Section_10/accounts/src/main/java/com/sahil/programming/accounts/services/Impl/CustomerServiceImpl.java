package com.sahil.programming.accounts.services.Impl;

import com.sahil.programming.accounts.Exception.ResourceNotFoundException;
import com.sahil.programming.accounts.Models.Accounts;
import com.sahil.programming.accounts.Models.Customer;
import com.sahil.programming.accounts.Repositories.AccountRepository;
import com.sahil.programming.accounts.Repositories.CustomerRepository;
import com.sahil.programming.accounts.dtos.*;
import com.sahil.programming.accounts.mapper.AccountsMapper;
import com.sahil.programming.accounts.mapper.CustomerMapper;
import com.sahil.programming.accounts.services.Client.CardsFeignClient;
import com.sahil.programming.accounts.services.Client.LoansFeignClient;
import com.sahil.programming.accounts.services.ICustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements ICustomerService {
    private CustomerRepository customerRepository;
    private AccountRepository accountRepository;
    private CardsFeignClient cardsFeignClient;
    private LoansFeignClient loansFeignClient;


    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber, String correlationId) {
        Optional<Customer> customerOptional = customerRepository.findByMobileNumber(mobileNumber);
        if(customerOptional.isEmpty()){
            throw new ResourceNotFoundException("Customer","mobileNumber",mobileNumber);
        }
        Customer customer = customerOptional.get();

        Optional<Accounts> accountsOptional = accountRepository.findByCustomerId(customer.getCustomerId());
        if (accountsOptional.isEmpty()){
            throw new ResourceNotFoundException("Account","customerId",customer.getCustomerId().toString());
        }
        Accounts accounts = accountsOptional.get();


        CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailDto(customer,new CustomerDetailsDto());
        customerDetailsDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts,new AccountsDto()));

        ResponseEntity<CardDto> cardDtoResponseEntity = cardsFeignClient.fetchCardDetails(correlationId,mobileNumber);
        CardDto cardDto = cardDtoResponseEntity.getBody();

        ResponseEntity<LoansDto> loansDtoResponseEntity = loansFeignClient.fetchLoanDetails(correlationId,mobileNumber);
        LoansDto loansDto = loansDtoResponseEntity.getBody();

        customerDetailsDto.setLoansDto(loansDto);
        customerDetailsDto.setCardsDto(cardDto);

        return customerDetailsDto;
    }
}
