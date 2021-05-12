package idv.codievilky.august.eleven.pinao.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther Codievilky August
 * @since 2021/5/12
 */
@RestController
public class TestController {
  @GetMapping(value = "/hello")
  public String hello() {
    return "Hello World！！！";
  }
}
