package idv.codievilky.august.eleven.pinao.server;

import com.mchange.v2.c3p0.AbstractComboPooledDataSource;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import idv.codievilky.august.eleven.pinao.shiro.LoginRealm;
import org.apache.shiro.spring.config.web.autoconfigure.ShiroWebFilterConfiguration;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.Ordered;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;

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
@EnableJdbcRepositories("idv.codievilky.august.eleven.pinao.store.repository")
public class SpringStartConfiguration extends ShiroWebFilterConfiguration implements WebMvcConfigurer {
  @Bean
  public ShiroFilterChainDefinition shiroFilterChainDefinition() {
    DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
    chainDefinition.addPathDefinition("/login.html", "authc"); // need to accept POSTs from the login form
    chainDefinition.addPathDefinition("/logout", "logout");
    chainDefinition.addPathDefinition("/**", "anon");
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

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/").setViewName("forward:/index.html");
    registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
  }

  @Bean
  @Lazy
  DataSource getDataSource() {
    AbstractComboPooledDataSource dataSource = new ComboPooledDataSource();
    dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/eleven?useUnicode=true&characterEncoding=utf-8");
    dataSource.setUser("eleven");
    dataSource.setPassword("981127");
    dataSource.setInitialPoolSize(1);
    dataSource.setMinPoolSize(1);
    dataSource.setMaxPoolSize(10);
    dataSource.setAcquireIncrement(1);
    dataSource.setMaxIdleTime(600);
    dataSource.setAcquireRetryAttempts(3);
    dataSource.setCheckoutTimeout(10000);
    dataSource.setPreferredTestQuery("SELECT 1");
    dataSource.setIdleConnectionTestPeriod(60);
    return dataSource;
  }

  @Bean
  @Lazy
  NamedParameterJdbcOperations namedParameterJdbcOperations(DataSource dataSource) {
    return new NamedParameterJdbcTemplate(dataSource);
  }

  @Override
  public void configurePathMatch(PathMatchConfigurer configurer) {
    configurer.addPathPrefix("/api", c -> c.isAnnotationPresent(RestController.class));
  }

}
