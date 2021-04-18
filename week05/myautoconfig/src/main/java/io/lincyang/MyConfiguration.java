package io.lincyang;

import io.lincyang.domain.Klass;
import io.lincyang.domain.School;
import io.lincyang.domain.Student;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * @Author lincyang
 * @Date 2021/4/18 6:19 PM
 **/
@Configuration
public class MyConfiguration {

    @Bean
    public Student student123() {
        return new Student(1, "zhangsan", "123");
    }

    @Bean
    public Student student100() {
        return new Student(2, "lisi", "100");
    }

    @Bean
    public Klass class1() {
        Klass klass = new Klass();
        klass.setStudents(Arrays.asList(student100(), student123()));
        return klass;
    }

    @Bean
    public School school() {
        return new School();
    }

}
