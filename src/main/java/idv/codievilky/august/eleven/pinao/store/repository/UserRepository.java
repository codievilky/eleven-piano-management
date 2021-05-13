package idv.codievilky.august.eleven.pinao.store.repository;

import idv.codievilky.august.eleven.pinao.store.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @auther Codievilky August
 * @since 2021/5/13
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
}
