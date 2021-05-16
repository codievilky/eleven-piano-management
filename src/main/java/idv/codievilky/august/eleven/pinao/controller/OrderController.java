package idv.codievilky.august.eleven.pinao.controller;

import idv.codievilky.august.eleven.pinao.common.ApiController;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @auther Codievilky August
 * @since 2021/5/16
 */
@ApiController
public class OrderController {

  @GetMapping("/order_info/my")
  public void getAllMyOrderInfo() {

  }

}
