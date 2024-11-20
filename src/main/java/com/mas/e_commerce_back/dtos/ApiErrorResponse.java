package com.mas.e_commerce_back.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//{"timestamp":"2018-04-11T05:56:03.845+0000","status":404,"error":"Not Found","message":"No message available","path":"/no-such-page"}
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiErrorResponse {
    private Integer status;
    private String error;
    private String timestamp;
}
