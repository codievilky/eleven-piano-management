package idv.codievilky.august.eleven.pinao.server;

import com.google.common.collect.Maps;
import idv.codievilky.august.eleven.pinao.common.exception.HttpResponseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

/**
 * @auther Codievilky August
 * @since 2021/5/13
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(value = Exception.class)
  public ResponseEntity<Map<String, String>> exception(Exception exception) {
    log.warn("request handled failed with exception:", exception);
    Map<String, String> result = Maps.newHashMap();
    result.put("error", exception.getLocalizedMessage());
    int responseCode;
    if (exception instanceof HttpResponseException) {
      responseCode = ((HttpResponseException) exception).getHttpResponseCode();
    } else {
      responseCode = 500;
    }
    return ResponseEntity.status(responseCode).body(result);
  }
}
