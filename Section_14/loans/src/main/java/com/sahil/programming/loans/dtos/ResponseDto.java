package com.sahil.programming.loans.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        name = "Response",
        description = "Schema to hold successful response information"
)
public class ResponseDto {
    @Schema(
            description = "Status code in the response"
    )
    private String StatusCode;

    @Schema(
            description = "Status message in the response"
    )
    private String StatusMessage;
}