package com.anhtrung.app_xu.dto;

import lombok.*;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class SuccessResponse {
    private String message;
    private Object data;
    
    public static SuccessResponse success(String message) {
        return SuccessResponse.builder().message(message).build();
    }
    
    public static SuccessResponse success(String message, Object data) {
        return SuccessResponse.builder().message(message).data(data).build();
    }
}
