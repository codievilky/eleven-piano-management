package idv.codievilky.august.eleven.pinao.store.repository;

import idv.codievilky.august.eleven.pinao.store.model.User;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @auther Codievilky August
 * @since 2021/5/13
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
  @Query("SELECT * FROM user WHERE username = :username")
  Optional<User> findUserByName(@Param("username") String username);
}
