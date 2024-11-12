package com.ahmed.a.habib.lazaapp.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Status {
    private int statusCode;
    private String statusMessage;

    public static Status buildStatusResponse(int statusCode, String message) {
        return Status.builder()
                .statusCode(statusCode)
                .statusMessage(message)
                .build();
    }
}