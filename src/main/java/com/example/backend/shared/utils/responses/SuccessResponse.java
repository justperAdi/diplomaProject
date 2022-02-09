package com.example.backend.shared.utils.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SuccessResponse {
    @JsonProperty("status")
    private String status = "success";

    @JsonProperty("data")
    private Object data;

    @JsonProperty("message")
    private String message;
}
