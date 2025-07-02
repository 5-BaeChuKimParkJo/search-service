package org.example.searchservice.common.exception;

import lombok.Getter;
import org.example.searchservice.common.response.BaseResponseStatus;

@Getter
public class BaseException extends RuntimeException{

    private final BaseResponseStatus status;
    public BaseException(BaseResponseStatus status) {
        this.status = status;
    }
}
