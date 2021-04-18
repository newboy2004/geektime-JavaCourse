package io.lincyang.spring.boot.domain;


import java.util.List;

public class Klass {

    List<io.lincyang.domain.Student> students;

    public void dong(){
        System.out.println(this.getStudents());
    }


    public List<io.lincyang.domain.Student> getStudents() {
        return students;
    }

    public void setStudents(List<io.lincyang.domain.Student> students) {
        this.students = students;
    }
}
