package io.lincyang;

import io.lincyang.domain.School;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @Author lincyang
 * @Date 2021/4/18 6:24 PM
 **/
@Component
public class TestAutoConfiguration implements CommandLineRunner {

    @Autowired
    School school;

    @Override
    public void run(String... args) throws Exception {
        school.ding();
    }
}
