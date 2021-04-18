package io.lincyang.jdbc;

import io.lincyang.entity.Student;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @Author lincyang
 * @Date 2021/4/18 8:53 PM
 **/
public class DBUtil {
    private static final String URL = "jdbc:mysql://localhost:336/mytest?useUnicode=true&characterEncoding=UTF-8";
    private static final String USER_NAME = "root";
    private static final String PASSWD = "123456";

    static {
        try {
            Class.forName("com.mysql.jdbc.Drive");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    private Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWD);
        return connection;
    }

    public void insertOne(Student stu) {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = getConnection();
            statement = connection.createStatement();
            statement.execute("insert into student values(" + stu.getId() + "," + stu.getName() + ")");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
            } catch (Exception e1) {

            }

            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteById(Integer id) {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = getConnection();
            statement = connection.createStatement();
            statement.executeUpdate("delete student where id=" + id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
            } catch (Exception e1) {

            }

            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void update(Student stu) {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = getConnection();
            statement = connection.createStatement();
            statement.executeUpdate("update student set name=" + stu.getName() + " where id=" + stu.getId());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
            } catch (Exception e1) {

            }

            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Student selectOne(Integer id) {
        Connection connection = null;
        Statement statement = null;
        Student stu = null;
        try {
            connection = getConnection();
            statement = connection.createStatement();
            stu = (Student) statement.executeQuery("select * from student from id=" + id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
            } catch (Exception e1) {

            }

            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return stu;
    }

}
