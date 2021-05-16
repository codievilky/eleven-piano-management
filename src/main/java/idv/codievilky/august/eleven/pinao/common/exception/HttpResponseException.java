package idv.codievilky.august.eleven.pinao.common.exception;

import lombok.Getter;

/**
 * @auther Codievilky August
 * @since 2021/5/16
 */
@Getter
public abstract class HttpResponseException extends RuntimeException {
  private int httpResponseCode;

  public HttpResponseException(int httpResponseCode) {
    this.httpResponseCode = httpResponseCode;
  }

  public HttpResponseException(String message, int httpResponseCode) {
    super(message);
    this.httpResponseCode = httpResponseCode;
  }

  public HttpResponseException(String message, Throwable cause, int httpResponseCode) {
    super(message, cause);
    this.httpResponseCode = httpResponseCode;
  }

  public HttpResponseException(Throwable cause, int httpResponseCode) {
    super(cause);
    this.httpResponseCode = httpResponseCode;
  }

  public HttpResponseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace,
      int httpResponseCode) {
    super(message, cause, enableSuppression, writableStackTrace);
    this.httpResponseCode = httpResponseCode;
  }
}
