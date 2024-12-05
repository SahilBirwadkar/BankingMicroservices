package com.sahil.programming.accounts.services.Impl;

import com.sahil.programming.accounts.Exception.CustomerAlreadyExistsException;
import com.sahil.programming.accounts.Exception.ResourceNotFoundException;
import com.sahil.programming.accounts.Models.Accounts;
import com.sahil.programming.accounts.Models.Customer;
import com.sahil.programming.accounts.Repositories.AccountRepository;
import com.sahil.programming.accounts.Repositories.CustomerRepository;
import com.sahil.programming.accounts.constants.AccountsConstants;
import com.sahil.programming.accounts.dtos.AccountsDto;
import com.sahil.programming.accounts.dtos.CustomerDto;
import com.sahil.programming.accounts.mapper.AccountsMapper;
import com.sahil.programming.accounts.mapper.CustomerMapper;
import com.sahil.programming.accounts.services.IAccountServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class AccountServicesImpl implements IAccountServices {
    private AccountRepository accountRepository;
    private CustomerRepository customerRepository;

    @Autowired
    public AccountServicesImpl(AccountRepository accountRepository, CustomerRepository customerRepository) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer=new Customer();
        customer.setName(customerDto.getName());
        customer.setEmail(customerDto.getEmail());
        customer.setMobileNumber(customerDto.getMobileNumber());


        Optional<Customer> customerOptional = customerRepository.findByMobileNumber(customerDto.getMobileNumber());
        System.out.println("Customer Details: "+customerDto.getMobileNumber());
        if (customerOptional.isPresent()){
            throw new CustomerAlreadyExistsException("Customer already registered with given mobileNumber "
                    +customerDto.getMobileNumber());
        }

        Customer newCustomer = customerRepository.save(customer);
        accountRepository.save(createNewAccount(newCustomer));
    }

    @Override
    public CustomerDto fetchAccount(String mobileNumber) {
        Optional<Customer> customerOptional = customerRepository.findByMobileNumber(mobileNumber);
        if (customerOptional.isEmpty()){
            throw new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber);
        }
        Customer customer = customerOptional.get();

        Optional<Accounts> accountsOptional = accountRepository.findByCustomerId(customer.getCustomerId());
        if (accountsOptional.isEmpty()){
            throw new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString());
        }
        Accounts accounts=accountsOptional.get();

        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());

        customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));
        return customerDto;
    }

    public boolean updateAccount(CustomerDto customerDto){
        boolean isUpdated = false;
        AccountsDto accountsDto = customerDto.getAccountsDto();
        if(accountsDto !=null ){
            Optional<Accounts> accountsOptional = accountRepository.findById(accountsDto.getAccountNumber());
            if (accountsOptional.isEmpty()){
                throw new ResourceNotFoundException("Account", "AccountNumber", accountsDto.getAccountNumber().toString());
            }
            Accounts accounts = accountsOptional.get();
            AccountsMapper.mapToAccounts(accountsDto,accounts);

            accountRepository.save(accounts);

            Optional<Customer> customerOptional = customerRepository.findById(accounts.getCustomerId());
            if (customerOptional.isEmpty()){
                throw new ResourceNotFoundException("Customer", "customerId", accounts.getCustomerId().toString());
            }
            Customer customer = customerOptional.get();
            CustomerMapper.mapToCustomer(customerDto,customer);
            customerRepository.save(customer);
            isUpdated = true;
        }
        return isUpdated;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {
        Optional<Customer> customerOptional = customerRepository.findByMobileNumber(mobileNumber);
        if(customerOptional.isEmpty()){
            throw new ResourceNotFoundException("Customer","mobileNumber",mobileNumber);
        }
        Customer customer = customerOptional.get();

        Optional<Accounts> accountsOptional = accountRepository.findByCustomerId(customer.getCustomerId());
        if(accountsOptional.isEmpty()){
            throw new ResourceNotFoundException("Account","customerId",customer.getCustomerId().toString());
        }
        Accounts accounts = accountsOptional.get();
        accountRepository.delete(accounts);
        return true;

    }

    private Accounts createNewAccount(Customer customer){
        Accounts account=new Accounts();

        account.setCustomerId(customer.getCustomerId());
        account.setAccountNumber(1000000000L + new Random().nextInt(900000000));
        account.setAccountType(AccountsConstants.SAVINGS);
        account.setBranchAddress(AccountsConstants.ADDRESS);
        return account;
    }

}
