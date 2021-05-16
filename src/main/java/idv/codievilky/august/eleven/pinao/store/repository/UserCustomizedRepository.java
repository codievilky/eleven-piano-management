package idv.codievilky.august.eleven.pinao.store.repository;

import idv.codievilky.august.eleven.pinao.store.model.User;

/**
 * @auther Codievilky August
 * @since 2021/5/16
 */
public interface UserCustomizedRepository {
  int update(User user);

  class UserCustomizedRepositoryImpl extends BaseCustomizedRepository implements UserCustomizedRepository {
    @Override
    public int update(User user) {
      return updateEntityNotNullFieldsById(user);
    }
  }
}
