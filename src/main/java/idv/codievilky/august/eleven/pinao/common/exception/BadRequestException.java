package idv.codievilky.august.eleven.pinao.common.exception;

/**
 * @auther Codievilky August
 * @since 2021/5/16
 */
public class BadRequestException extends HttpResponseException {
  public BadRequestException(String message) {
    super(message, 400);
  }
}
