import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Objects;

/**
 * @date 2021/3/28 20:24
 */
public class MyHttp {
    public static void main(String[] args) {
        CloseableHttpClient client = HttpClientBuilder.create().build();

        HttpGet httpGet = new HttpGet("http://localhost:8801");

        CloseableHttpResponse response = null;
        try {
            response = client.execute(httpGet);
            HttpEntity httpEntity = response.getEntity();
            System.out.println("response status: " + response.getStatusLine());
            if (!Objects.isNull(httpEntity)) {
                System.out.println("contentLength: " + httpEntity.getContentLength());
                System.out.println("content: " + EntityUtils.toString(httpEntity));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (client != null) {
                    client.close();
                }

                if (response != null) {
                    response.close();
                }
            } catch (IOException ex) {

            }
        }
    }
}
