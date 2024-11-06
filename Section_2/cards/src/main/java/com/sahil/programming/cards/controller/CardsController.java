package com.sahil.programming.cards.controller;

import com.sahil.programming.cards.constants.CardsConstants;
import com.sahil.programming.cards.dtos.CardDto;
import com.sahil.programming.cards.dtos.ErrorResponseDto;
import com.sahil.programming.cards.dtos.ResponseDto;
import com.sahil.programming.cards.services.ICardsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Tag(
        name = "CRUD REST APIs for Cards",
        description = "CRUD REST APIs to create, update, fetch and delete Card details"
)
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@RestController
public class CardsController {
    private ICardsService iCardsService;

    @Autowired
    public CardsController(ICardsService iCardsService) {
        this.iCardsService = iCardsService;
    }

    @Operation(
            summary = "Create Card REST APIs",
            description = "REST API to create new card"
    )
    @ApiResponses({
        @ApiResponse(
                responseCode = "201",
                description = "HTTP Status OK"
        ),
        @ApiResponse(
                responseCode = "500",
                description = "Card creation failed. Please try again or contact Dev team",
                content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
        )
    })
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createCard(@RequestParam @Pattern(regexp = "(^$|[0-9]{10})") String mobileNumber){
        iCardsService.createCard(mobileNumber);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(CardsConstants.STATUS_201, CardsConstants.MESSAGE_201));
    }

    @Operation(
            summary = "Fetch Card Details REST APIs",
            description = "REST API to fetch card details"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Fail to fetch card details. Please try again or contact Dev team",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            )
    })
    @GetMapping("/fetch")
    public ResponseEntity<CardDto> fetchCardDetails(@RequestParam String mobileNumber){
        CardDto cardDto = iCardsService.fetchCardDetails(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(cardDto);
    }


    @Operation(
            summary = "Update Card Details REST APIs",
            description = "REST API to update card details"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Http status Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            )
    })
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateCardDetails(@RequestBody CardDto cardDto){
        boolean isUpdated = iCardsService.updateCardDetails(cardDto);

        if (isUpdated){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseDto(CardsConstants.STATUS_200, CardsConstants.MESSAGE_200)
            );
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseDto(CardsConstants.STATUS_417, CardsConstants.MESSAGE_417_UPDATE)
            );
        }
    }

    @Operation(
            summary = "Delete Card Details REST APIs",
            description = "REST API to delete card details"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Http status Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            )
    })
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteCardDetails(@RequestParam String mobileNumber){
        boolean isDeleted = iCardsService.deleteCardDetails(mobileNumber);
        if (isDeleted){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseDto(CardsConstants.STATUS_200, CardsConstants.MESSAGE_200)
            );
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseDto(CardsConstants.STATUS_417, CardsConstants.MESSAGE_417_UPDATE)
            );
        }
    }
}
