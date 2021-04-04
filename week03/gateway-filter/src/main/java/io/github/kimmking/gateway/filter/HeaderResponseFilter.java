package io.github.kimmking.gateway.filter;

import io.netty.handler.codec.http.FullHttpResponse;

/**
 * @Author lincyang
 * @Date 2021/4/4 10:03 PM
 **/
public class HeaderResponseFilter implements HttpResponseFilter {

    @Override
    public void filter(FullHttpResponse response) {
        response.headers().add("my", "first netty gateway");
    }
}
