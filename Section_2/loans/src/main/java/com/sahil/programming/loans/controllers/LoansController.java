package com.sahil.programming.loans.controllers;

import com.sahil.programming.loans.Exception.ResourceNotFoundException;
import com.sahil.programming.loans.Models.Loans;
import com.sahil.programming.loans.constant.LoansConstants;
import com.sahil.programming.loans.dtos.ErrorResponseDto;
import com.sahil.programming.loans.dtos.LoansDto;
import com.sahil.programming.loans.dtos.ResponseDto;
import com.sahil.programming.loans.services.ILoanServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "CRUD REST APIs for Loans",
        description = "CRUD REST APIs to CREATE, UPDATE, FETCH AND DELETE loan details"
)
@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
public class LoansController {
    private ILoanServices iLoanServices;

    @Autowired
    public LoansController(ILoanServices iLoanServices) {
        this.iLoanServices = iLoanServices;
    }

    @Operation(
            summary = "Create Loan REST API",
            description = "REST API to create new loan inside EazyBank"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createLoan(@RequestParam String mobileNumber){
        iLoanServices.createLoan(mobileNumber);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseDto(LoansConstants.STATUS_201,
                        LoansConstants.MESSAGE_201)
        );
    }

    @Operation(
            summary = "Fetch loan details REST API",
            description = "REST API to fetch loan details"
    )
    @ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "HTTP Status OK"
        ),
        @ApiResponse(
                responseCode = "500",
                description = "HTTP Status Internal Server Error",
                content = @Content(
                        schema = @Schema(implementation = ErrorResponseDto.class)
                )
        )
    })
    @GetMapping("/fetch")
    public ResponseEntity<LoansDto> fetchLoan(@RequestParam String mobileNumber) throws ResourceNotFoundException {
        LoansDto loansDto = iLoanServices.fetchLoan(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(loansDto);
    }

    @Operation(
        summary = "Update loan details REST API",
            description = "REST API to update loan details"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "HTTP Status OK"
        ),
        @ApiResponse(
            responseCode = "200",
            description = "HTTP Status OK"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "HTTP Status Internal Server Error",
            content = @Content(
                schema = @Schema(implementation = ErrorResponseDto.class)
            )
        )
    })
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateLoanDetails(@RequestBody LoansDto loansDto) throws ResourceNotFoundException {
        boolean isUpdated = iLoanServices.updateLoanDetails(loansDto);

        if (isUpdated){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseDto(LoansConstants.STATUS_200,LoansConstants.MESSAGE_200)
            );
        }else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
                    new ResponseDto(LoansConstants.STATUS_417,LoansConstants.MESSAGE_417_UPDATE)
            );
        }
    }


    @Operation(
            summary = "Delete Loan Details REST API",
            description = "REST API to delete Loan details based on a mobile number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteLoanDetails(@RequestParam String mobileNumber) throws ResourceNotFoundException {
        boolean isDeleted = iLoanServices.deleteLoanDetails(mobileNumber);

        if (isDeleted){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseDto(LoansConstants.STATUS_200,LoansConstants.MESSAGE_200)
            );
        }else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
                    new ResponseDto(LoansConstants.STATUS_417,LoansConstants.MESSAGE_417_DELETE)
            );
        }
    }
}
