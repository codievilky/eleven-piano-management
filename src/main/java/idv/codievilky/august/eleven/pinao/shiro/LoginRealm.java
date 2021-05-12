package idv.codievilky.august.eleven.pinao.shiro;

import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

/**
 * @auther Codievilky August
 * @since 2021/5/12
 */
@Component
public class LoginRealm extends AuthorizingRealm {
  public LoginRealm() {
    setCachingEnabled(true);
    setAuthenticationCachingEnabled(true);
    setAuthenticationCacheName("authenticationCache");
  }

  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    return null;
  }

  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
    if ("admin".equals(token.getPrincipal()) && "123".equals(new String((char[]) token.getCredentials()))) {
      return new SimpleAuthenticationInfo("admin", "123", getName());
    }
    throw new AccountException("Login Failed");
  }
}
