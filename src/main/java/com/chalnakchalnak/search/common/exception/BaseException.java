package com.chalnakchalnak.search.common.exception;

import com.chalnakchalnak.search.common.response.BaseResponseStatus;
import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {

  private final BaseResponseStatus status;

  public BaseException(BaseResponseStatus status) {
    this.status = status;
  }
}
