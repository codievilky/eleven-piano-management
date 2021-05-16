package idv.codievilky.august.eleven.pinao.controller;

import idv.codievilky.august.eleven.pinao.common.exception.NotFoundException;
import idv.codievilky.august.eleven.pinao.request.LoginInfo;
import idv.codievilky.august.eleven.pinao.store.model.User;
import idv.codievilky.august.eleven.pinao.store.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther Codievilky August
 * @since 2021/5/12
 */
@RestController
public class LoginController {
  @Autowired
  private UserService userService;

  @PostMapping("/api/login")
  public User login(@RequestBody LoginInfo loginInfo) {
    Subject subject = SecurityUtils.getSubject();
    subject.login(new UsernamePasswordToken(loginInfo.getUsername(), loginInfo.getPassword()));
    return userService.getUserByName(loginInfo.getUsername()).orElseThrow(() -> new NotFoundException("找不到对应用户"));
  }
}
