package com.abnamro.tvshowmanager.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;


@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class Response<T> {
    @JsonInclude // Even when false, success needs to be serialized
    private boolean success = false;
    private String message;
    private ZonedDateTime timestamp = ZonedDateTime.now();
    private T Data;

    public Response(String message) {
        this.message = message;
    }

    public static <T> Response<T> failedResponse(String message) {
        return new Response<>(message);
    }

    public static <T> Response<T> successResponse(T data) {
        Response<T> response = new Response<>();
        response.setSuccess(true);
        response.setData(data);
        return response;
    }
}
