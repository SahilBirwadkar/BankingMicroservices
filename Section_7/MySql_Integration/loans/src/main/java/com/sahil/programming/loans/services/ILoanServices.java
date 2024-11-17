package com.sahil.programming.loans.services;

import com.sahil.programming.loans.Exception.ResourceNotFoundException;
import com.sahil.programming.loans.dtos.LoansDto;

import java.util.List;

public interface ILoanServices {
    public void createLoan(String mobileNumber);
    public LoansDto fetchLoan(String mobileNumber) throws ResourceNotFoundException;
    public boolean updateLoanDetails(LoansDto loansDto) throws ResourceNotFoundException;
    public boolean deleteLoanDetails(String mobileNumber) throws ResourceNotFoundException;

    List<LoansDto> fetchAllLoans();
}
