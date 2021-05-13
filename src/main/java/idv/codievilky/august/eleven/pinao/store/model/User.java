package idv.codievilky.august.eleven.pinao.store.model;

import idv.codievilky.august.eleven.pinao.common.LoginRole;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Date;

/**
 * @auther Codievilky August
 * @since 2021/5/13
 */
@Table("user")
@Data
public class User {
  @Id
  private Integer id;
  private String username;
  private LoginRole role;
  private String password;
  private String phoneNumber;
  @ReadOnlyProperty
  private Date createTime;
}
