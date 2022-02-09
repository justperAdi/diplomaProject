package com.example.backend.exceptions;

import com.example.backend.shared.utils.codes.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ServiceException extends RuntimeException {

    protected ErrorCode errorCode;

    private String message;
}
