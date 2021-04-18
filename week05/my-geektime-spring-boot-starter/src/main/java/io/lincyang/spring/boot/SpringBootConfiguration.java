
package io.lincyang.spring.boot;


import io.lincyang.spring.boot.domain.Klass;
import io.lincyang.spring.boot.domain.School;
import io.lincyang.spring.boot.domain.Student;

import java.util.Arrays;

@Configuration
public class SpringBootConfiguration {
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
