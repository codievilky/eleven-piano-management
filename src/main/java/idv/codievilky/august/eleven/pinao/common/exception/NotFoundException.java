package idv.codievilky.august.eleven.pinao.common.exception;

/**
 * @auther Codievilky August
 * @since 2021/5/16
 */
public class NotFoundException extends HttpResponseException {
  public NotFoundException(String message) {
    super(message, 404);
  }
}
