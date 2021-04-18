import io.lincyang.School;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author lincyang
 * @Date 2021/4/18 4:52 PM
 **/
public class Main {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        School school = (School) context.getBean("school");
        school.ding();
    }
}
