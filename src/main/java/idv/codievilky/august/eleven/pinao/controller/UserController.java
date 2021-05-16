package idv.codievilky.august.eleven.pinao.controller;

import idv.codievilky.august.eleven.pinao.common.exception.BadRequestException;
import idv.codievilky.august.eleven.pinao.common.exception.NotFoundException;
import idv.codievilky.august.eleven.pinao.store.model.User;
import idv.codievilky.august.eleven.pinao.store.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @auther Codievilky August
 * @since 2021/5/16
 */
@RestController
public class UserController {
  @Autowired
  private UserService userService;

  @PostMapping("/user_info")
  public User updateUserInfo(@RequestBody User user) {
    Optional<User> userByName = userService.getUserByName(user.getUsername());
    userByName.orElseThrow(() -> new NotFoundException("找不到对应用户：" + user.getUsername()));
    Subject subject = SecurityUtils.getSubject();
    if (!user.getUsername().equals(subject.getPrincipal())) {
      throw new BadRequestException("只能修改自己的用户属性");
    }
    userService.
  }

}
