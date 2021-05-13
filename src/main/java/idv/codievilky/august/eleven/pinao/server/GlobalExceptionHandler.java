package idv.codievilky.august.eleven.pinao.server;

import com.google.common.collect.Maps;
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
    return ResponseEntity.status(400).body(result);
  }
}
