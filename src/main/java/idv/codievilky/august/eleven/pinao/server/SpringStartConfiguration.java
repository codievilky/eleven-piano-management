package idv.codievilky.august.eleven.pinao.server;

import idv.codievilky.august.eleven.pinao.shiro.LoginRealm;
import org.apache.shiro.spring.config.web.autoconfigure.ShiroWebFilterConfiguration;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * @auther Codievilky August
 * @since 2021/5/12
 */
@Configuration
@ComponentScan(
    basePackages = "idv.codievilky.august.eleven.pinao",
    useDefaultFilters = false,
    includeFilters = {
        @ComponentScan.Filter(Service.class),
        @ComponentScan.Filter(Component.class),
        @ComponentScan.Filter(Repository.class)
    },
    lazyInit = true
)
public class SpringStartConfiguration extends ShiroWebFilterConfiguration {
  @Bean
  public ShiroFilterChainDefinition shiroFilterChainDefinition() {
    DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
    chainDefinition.addPathDefinition("/login.html", "authc"); // need to accept POSTs from the login form
    chainDefinition.addPathDefinition("/logout", "logout");
    chainDefinition.addPathDefinition("/hello", "authc");
    return chainDefinition;
  }

  @Override
  protected ShiroFilterFactoryBean shiroFilterFactoryBean() {
    ShiroFilterFactoryBean shiroFilterFactoryBean = super.shiroFilterFactoryBean();
    shiroFilterFactoryBean.setLoginUrl("/api/login");
    return shiroFilterFactoryBean;
  }

  @Bean
  public DefaultWebSecurityManager securityManager(LoginRealm realm) {
    DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
    manager.setRealm(realm);
    return manager;
  }

}
