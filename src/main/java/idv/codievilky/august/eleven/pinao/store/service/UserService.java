package idv.codievilky.august.eleven.pinao.store.service;

import com.google.common.collect.Lists;
import idv.codievilky.august.eleven.pinao.store.model.User;
import idv.codievilky.august.eleven.pinao.store.repository.UserCustomizedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @auther Codievilky August
 * @since 2021/5/13
 */
@Service
public class UserService {
  @Autowired
  private UserCustomizedRepository userRepository;

  public List<User> listAllUser() {
    return Lists.newArrayList(userRepository.findAll());
  }

  public Optional<User> getUserByName(String username) {
    return userRepository.findUserByName(username);
  }
}
