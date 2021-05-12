package idv.codievilky.august.eleven.pinao.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;

/**
 * @auther Codievilky August
 * @since 2021/5/12
 */
@SpringBootApplication
@Import(SpringStartConfiguration.class)
public class PianoApplication extends SpringBootServletInitializer {
  private static ApplicationContext applicationContext;

  public <T> T getBean(Class<T> requiredType) {
    return applicationContext.getBean(requiredType);
  }

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
    return builder.sources(PianoApplication.class);
  }

  public static void main(String[] args) {
    PianoApplication.applicationContext = SpringApplication.run(PianoApplication.class, args);
  }
}
