package io.github.kimmking.gateway.outbound;

import io.github.kimmking.gateway.filter.HeaderResponseFilter;
import io.github.kimmking.gateway.filter.HttpRequestFilter;
import io.github.kimmking.gateway.filter.HttpResponseFilter;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @Author lincyang
 * @Date 2021/4/4 9:18 PM
 **/
public class HttpOutboundHandler {

    private ThreadPoolExecutor proxyServerPool;
    private List<String> backendUrls;
    private CloseableHttpClient client;

    HttpResponseFilter responseFilter = new HeaderResponseFilter();

    public HttpOutboundHandler(List<String> proxyServers) {
        this.backendUrls = proxyServers.stream().map(this::formatUrl).collect(Collectors.toList());
        this.client = HttpClientBuilder.create().build();

        int cores = Runtime.getRuntime().availableProcessors();
        long keepalives = 100;
        int queueSizes = 100;
        ThreadFactory threadFactory = new ThreadFactory() {
            AtomicInteger counter = new AtomicInteger(0);

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "proxyServerPool-" + counter.getAndIncrement());
            }
        };
        RejectedExecutionHandler rejectedExecutionHandler = new ThreadPoolExecutor.CallerRunsPolicy();
        this.proxyServerPool = new ThreadPoolExecutor(cores, 2 * cores + 1, keepalives, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(queueSizes), threadFactory, rejectedExecutionHandler);
    }


    private String formatUrl(String backendUrl) {
        return backendUrl.indexOf("/") > -1 ? backendUrl.substring(0, backendUrl.length() - 1) : backendUrl;
    }

    public void handle(FullHttpRequest fullRequest, ChannelHandlerContext ctx, HttpRequestFilter filter) {
        // 每次都从后端服务中选取第一个服务作为请求地址
        String url = this.backendUrls.get(0);

        //TODO 添加过滤器
        filter.filter(fullRequest, ctx);
        proxyServerPool.submit(() -> fetchGet(fullRequest, ctx, url));
    }

    private void fetchGet(FullHttpRequest fullRequest, ChannelHandlerContext ctx, String url) {
        HttpGet httpGet = new HttpGet("http://localhost:8801");

        CloseableHttpResponse response = null;
        try {
            response = client.execute(httpGet);
            handleResponse(fullRequest, ctx, response);
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

    private void handleResponse(FullHttpRequest fullRequest, ChannelHandlerContext ctx, HttpResponse endpointResponse) {
        FullHttpResponse response = null;
        try {
            byte[] body = EntityUtils.toByteArray(endpointResponse.getEntity());
            response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.wrappedBuffer(body));

            response.headers().set("Content-Type", "application/json");
            response.headers().set("Content-length", Integer.valueOf(endpointResponse.getFirstHeader("Content-Length").getValue()));

            responseFilter.filter(response);
        } catch (Exception e) {
            e.printStackTrace();
            response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.NO_CONTENT);
            ctx.close();
        } finally {
            if (fullRequest != null) {
                if (!HttpUtil.isKeepAlive(fullRequest)) {
                    ctx.write(response).addListener(ChannelFutureListener.CLOSE);
                } else {
                    ctx.write(response);
                }
            }
            ctx.flush();
        }
    }
}

