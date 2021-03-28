import java.io.*;
import java.lang.reflect.Method;

/**
 * @author lincyang
 * @date 2021-03-21 16:44
 **/
public class HelloClassLoader extends ClassLoader {

    public static void main(String[] args) throws Exception {
        Class<?> aClass = new HelloClassLoader().findClass("Hello.xlass");
        Object obj = aClass.newInstance();
        Method method = aClass.getMethod("hello");
        method.invoke(obj);
    }

    @Override
    protected Class<?> findClass(String name) {
        byte[] b = new byte[0];

        try {
            b = readBytes(name);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return defineClass(name.substring(0, name.indexOf(".")), b, 0, b.length);
    }

    private byte[] readBytes(String name) throws IOException {
        File classFile = new File(this.getClass().getClassLoader().getResource(name).getPath());

        InputStream is = null;
        ByteArrayOutputStream baos = null;
        byte[] data = new byte[1024];

        byte[] bytes = null;
        try {
            is = new FileInputStream(classFile);
            baos = new ByteArrayOutputStream();

            int length;
            while ((length = is.read(data)) != -1) {
                baos.write(data, 0, length);
            }

            bytes = baos.toByteArray();

            for (int i = 0; i < bytes.length; i++) {
                bytes[i] = (byte) (255 - bytes[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                is.close();
            }
            if (baos != null) {
                baos.close();
            }
        }

        return bytes;
    }
}
