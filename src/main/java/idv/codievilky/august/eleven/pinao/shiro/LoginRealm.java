package idv.codievilky.august.eleven.pinao.shiro;

import idv.codievilky.august.eleven.pinao.store.model.User;
import idv.codievilky.august.eleven.pinao.store.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

/**
 * @auther Codievilky August
 * @since 2021/5/12
 */
@Component
public class LoginRealm extends AuthorizingRealm {
  @Autowired
  private UserService userService;

  public LoginRealm() {
    setCachingEnabled(true);
    setAuthenticationCachingEnabled(true);
    setAuthenticationCacheName("authenticationCache");
    class SimpleMatcher implements CredentialsMatcher {
      @Override
      public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        return StringUtils.equals((String) info.getCredentials(),
            DigestUtils.md5DigestAsHex((byte[]) token.getCredentials()));
      }
    }
    setCredentialsMatcher(new SimpleMatcher());
  }

  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    return null;
  }

  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
    String username = (String) token.getPrincipal();
    User user = userService.getUserByName(username).orElseThrow(() -> new UnknownAccountException("找不到对应用户"));
    return new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(), getName());
  }
}
