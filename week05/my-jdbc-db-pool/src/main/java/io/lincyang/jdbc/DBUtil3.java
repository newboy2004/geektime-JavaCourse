package io.lincyang.jdbc;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.lincyang.entity.Student;

import javax.sql.DataSource;
import java.sql.*;

/**
 * @Author lincyang
 * @Date 2021/4/18 8:53 PM
 **/
public class DBUtil3 {

    private static final DataSource dataSourceFromHikari =
            new HikariDataSource(new HikariConfig("hikari.properties"));

    static {
        try {
            Class.forName("com.mysql.jdbc.Drive");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    private Connection getConnection() throws SQLException {
        Connection connection = dataSourceFromHikari.getConnection();
        return connection;
    }

    public void insertOne(Student stu) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("insert into student values(?,?)");
            statement.setInt(1, stu.getId());
            statement.setString(2, stu.getName());
            statement.executeUpdate();
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
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("delete student where id=?");
            statement.setInt(1, id);
            statement.executeUpdate();
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
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("update student set name=? where id=?");
            statement.setString(1, stu.getName());
            statement.setInt(2, stu.getId());
            statement.executeUpdate();
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
        PreparedStatement statement = null;
        Student stu = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("select * from student from id=?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                stu = new Student();
                stu.setId(resultSet.getInt("id"));
                stu.setName(resultSet.getString("name"));
            }
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
