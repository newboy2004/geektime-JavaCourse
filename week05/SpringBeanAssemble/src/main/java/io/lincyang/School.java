package io.lincyang;

import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

public class School {

    // Resource
    @Autowired(required = true) //primary
    Klass class1;

    @Resource(name = "student100")
    Student student100;

    public void ding(){

        System.out.println("Class1 have " + this.class1.getStudents().size() + " students and one is " + this.student100);

    }

}