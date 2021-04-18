package io.lincyang.domain;


public class Student {


    private int id;
    private String name;

    private String beanName;

    public Student() {
    }

    public Student(int id, String name, String beanName) {
        this.id = id;
        this.name = name;
        this.beanName = beanName;
    }

    public void print() {
        System.out.println(this.beanName);
    }


    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", beanName='" + beanName + '\'' +
                '}';
    }
}
