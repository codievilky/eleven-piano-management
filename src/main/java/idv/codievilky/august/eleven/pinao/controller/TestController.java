package idv.codievilky.august.eleven.pinao.controller;

import idv.codievilky.august.eleven.pinao.store.model.User;
import idv.codievilky.august.eleven.pinao.store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @auther Codievilky August
 * @since 2021/5/12
 */
@RestController
public class TestController {
  @Autowired
  private UserService userService;

  @GetMapping(value = "/hello")
  public List<User> hello() {
    return userService.listAllUser();
  }
}
