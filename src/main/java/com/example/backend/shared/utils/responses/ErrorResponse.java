package com.example.backend.shared.utils.responses;

import com.example.backend.shared.utils.codes.ErrorCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    @JsonProperty("status")
    private String status = "success";

    @JsonProperty("code")
    private ErrorCode errorCode;

    @JsonProperty("message")
    private String message;
}
