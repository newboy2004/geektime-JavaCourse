package io.lincyang;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @Author lincyang
 * @Date 2021/4/18 6:16 PM
 **/
@Configuration
@Import({MyConfiguration.class})
public class MyAutoConfiguration {
}
