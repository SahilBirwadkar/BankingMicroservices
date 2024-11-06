package com.sahil.programming.loans.services;

import com.sahil.programming.loans.Exception.LoanAlreadyExistsException;
import com.sahil.programming.loans.Exception.ResourceNotFoundException;
import com.sahil.programming.loans.Models.Loans;
import com.sahil.programming.loans.constant.LoansConstants;
import com.sahil.programming.loans.dtos.LoansDto;
import com.sahil.programming.loans.mapper.LoansMapper;
import com.sahil.programming.loans.repositories.LoanRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class LoanServicesImpl implements ILoanServices{

    public LoanRepository loanRepository;

    public LoanServicesImpl(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    @Override
    public void createLoan(String mobileNumber) {
        Optional<Loans> loansOptional = loanRepository.findByMobileNumber(mobileNumber);
        if (loansOptional.isPresent()){
            throw new LoanAlreadyExistsException("Loan Already Exists");
        }
        loanRepository.save(createNewLoan(mobileNumber));
    }

    private Loans createNewLoan(String mobileNumber) {

        Loans loans = new Loans();
        long randomLoanNumber = 100000000000L+new Random().nextInt(900000000);
        loans.setLoanNumber(Long.toString(randomLoanNumber));
        loans.setMobileNumber(mobileNumber);
        loans.setLoanType(LoansConstants.HOME_LOAN);
        loans.setTotalLoan(LoansConstants.NEW_LOAN_LIMIT);
        loans.setAmountPaid(0);
        loans.setOutstandingAmount(LoansConstants.NEW_LOAN_LIMIT);
        return loans;
    }

    @Override
    public LoansDto fetchLoan(String mobileNumber) throws ResourceNotFoundException {
        Optional<Loans> loansOptional = loanRepository.findByMobileNumber(mobileNumber);
        if (loansOptional.isEmpty()){
            throw new ResourceNotFoundException("Loan", "Mobile Number", mobileNumber);
        }

        return LoansMapper.mapToLoansDto(loansOptional.get(), new LoansDto());
    }

    @Override
    public boolean updateLoanDetails(LoansDto loansDto) throws ResourceNotFoundException{
        Optional<Loans> loansOptional = loanRepository.findByLoanNumber(loansDto.getLoanNumber());
        if (loansOptional.isEmpty()){
            throw new ResourceNotFoundException("Loan","Loan Number", loansDto.getLoanNumber());
        }

        Loans loans = LoansMapper.mapToLoans(loansDto,loansOptional.get());
        loanRepository.save(loans);
        return true;
    }

    @Override
    public boolean deleteLoanDetails(String mobileNumber) throws ResourceNotFoundException {
        Optional<Loans> loansOptional = loanRepository.findByMobileNumber(mobileNumber);
        if (loansOptional.isEmpty()){
            throw new ResourceNotFoundException("Loan","Mobile Number", mobileNumber);
        }

        Loans loans = loansOptional.get();
        loanRepository.deleteById(loans.getLoanId());
        return true;
    }

    @Override
    public List<LoansDto> fetchAllLoans() {
        List<Loans> loans = loanRepository.findAll();
        List<LoansDto> loansDtoList = new ArrayList<>();
        for (int i=0; i<loans.size(); i++){
            LoansDto loansDto = LoansMapper.mapToLoansDto(loans.get(i), new LoansDto());
            loansDtoList.add(loansDto);
        }
        return loansDtoList;
    }
}
