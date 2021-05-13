package idv.codievilky.august.eleven.pinao.controller;

import idv.codievilky.august.eleven.pinao.request.LoginInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther Codievilky August
 * @since 2021/5/12
 */
@RestController
public class LoginController {
  @PostMapping("/api/login")
  public String login(@RequestBody LoginInfo loginInfo) {
    Subject subject = SecurityUtils.getSubject();
    subject.login(new UsernamePasswordToken(loginInfo.getUsername(), loginInfo.getPassword()));
    return "succeed";
  }
}
