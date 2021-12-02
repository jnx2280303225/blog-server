//package com.jnx.blogserver.config;
//
//import com.popicorns.scm.infrastructure.utils.FastJsonUtil;
//import feign.Request;
//import feign.RequestInterceptor;
//import feign.Response;
//import org.apache.commons.lang3.concurrent.BasicThreadFactory;
//import org.apache.commons.lang3.time.FastDateFormat;
//import org.apache.http.HeaderElement;
//import org.apache.http.HeaderElementIterator;
//import org.apache.http.config.Registry;
//import org.apache.http.config.RegistryBuilder;
//import org.apache.http.conn.socket.ConnectionSocketFactory;
//import org.apache.http.conn.socket.PlainConnectionSocketFactory;
//import org.apache.http.conn.ssl.NoopHostnameVerifier;
//import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
//import org.apache.http.impl.client.HttpClientBuilder;
//import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
//import org.apache.http.message.BasicHeaderElementIterator;
//import org.apache.http.protocol.HTTP;
//import org.apache.http.ssl.SSLContextBuilder;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
//import org.springframework.cloud.openfeign.EnableFeignClients;
//import org.springframework.cloud.openfeign.FeignFormatterRegistrar;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.core.convert.converter.Converter;
//import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
//import org.springframework.http.converter.HttpMessageConverter;
//import org.springframework.http.converter.StringHttpMessageConverter;
//import org.springframework.lang.NonNull;
//import org.springframework.web.client.RestTemplate;
//
//import javax.annotation.PreDestroy;
//import javax.net.ssl.HostnameVerifier;
//import javax.net.ssl.SSLContext;
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.LocalTime;
//import java.time.format.DateTimeFormatter;
//import java.util.Date;
//import java.util.List;
//import java.util.concurrent.ScheduledExecutorService;
//import java.util.concurrent.ScheduledThreadPoolExecutor;
//import java.util.concurrent.TimeUnit;
//
//
///**
// * Feign配置
// *
// * @author 蒋楠鑫
// * @since 2021/12/2
// */
//@Configuration
//@EnableDiscoveryClient
//@EnableFeignClients(basePackages = "com.popicorns")
//public class FeignConfig {
//
//    private final static ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1,
//            new BasicThreadFactory.Builder().namingPattern("httpclient-schedule-pool-%d").daemon(true).build());
//
//    /**
//     * FeignAutoConfiguration
//     *
//     * @return
//     * @throws Exception
//     */
//    @Primary
//    @Bean(destroyMethod = "close")
//    public CloseableHttpClient closeableHttpClient() throws Exception {
//        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
//        SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, (arg0, arg1) -> true).build();
//        httpClientBuilder.setSSLContext(sslContext);
//        HostnameVerifier hostnameVerifier = NoopHostnameVerifier.INSTANCE;
//        SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext,
//                hostnameVerifier);
//        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
//                .register("http", PlainConnectionSocketFactory.getSocketFactory())
//                .register("https", sslConnectionSocketFactory).build();
//        PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager(
//                socketFactoryRegistry);
//        poolingHttpClientConnectionManager.setMaxTotal(200);
//        poolingHttpClientConnectionManager.setDefaultMaxPerRoute(20);
//        httpClientBuilder.setConnectionManager(poolingHttpClientConnectionManager);
//        httpClientBuilder.setRetryHandler(new DefaultHttpRequestRetryHandler(3, true));
//        httpClientBuilder.setKeepAliveStrategy((response, context) -> {
//            HeaderElementIterator it = new BasicHeaderElementIterator
//                    (response.headerIterator(HTTP.CONN_KEEP_ALIVE));
//            while (it.hasNext()) {
//                HeaderElement he = it.nextElement();
//                String param = he.getName();
//                String value = he.getValue();
//                if (value != null && param.equalsIgnoreCase
//                        ("timeout")) {
//                    return Long.parseLong(value) * 1000;
//                }
//            }
//            //如果没有约定，则默认定义时长为60s
//            return 60 * 1000;
//        });
//        executorService.scheduleAtFixedRate(() -> poolingHttpClientConnectionManager.closeExpiredConnections(), 30000, 3000, TimeUnit.MILLISECONDS);
//        return httpClientBuilder.build();
//    }
//
//    @Bean
//    @Primary
//    //@LoadBalanced
//    public RestTemplate httpClientRestTemplate(@Autowired CloseableHttpClient httpClient) {
//        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
//        requestFactory.setConnectTimeout(10 * 1000);
//        requestFactory.setReadTimeout(12 * 1000);
//        RestTemplate restTemplate = new RestTemplate(requestFactory);
//        List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
//        for (int i = 0; i < messageConverters.size(); i++) {
//            HttpMessageConverter<?> httpMessageConverter = messageConverters.get(i);
//            if (httpMessageConverter instanceof StringHttpMessageConverter) {
//                messageConverters.set(i, new StringHttpMessageConverter(StandardCharsets.UTF_8));
//            }
//        }
//        restTemplate.setMessageConverters(messageConverters);
//        return restTemplate;
//    }
//
//    //@Bean
//    //public ApacheHttpClient apacheHttpClient(@Autowired CloseableHttpClient httpClient) {
//    // FeignRibbonClientAutoConfiguration HttpClientFeignConfiguration
//    // LoadBalancerFeignClient HttpClientFeignConfiguration
//    // return new ApacheHttpClient(httpClient);
//    //}
//
//    @PreDestroy
//    public void destroy() {
//        executorService.shutdown();
//    }
//
//    /**
//     * 方法描述：feign日志等级
//     */
//    @Bean
//    feign.Logger.Level feignLevel() {
//        return feign.Logger.Level.FULL;
////        return feign.Logger.Level.NONE;
//    }
//
//
//    @Bean
//    public RequestInterceptor requestInterceptor1() {
//        RequestInterceptor requestInterceptor = template -> {
//            template.header("serviceName", "ec-scm-service");
//            template.header("Content-Type", "application/json; charset=UTF-8");
//        };
//        return requestInterceptor;
//    }
//
//    @Bean
//    public FeignFormatterRegistrar dateFormatRegister() {
//        return registry -> registry.addConverter(new Date2StringConverter());
//    }
//
//    @Bean
//    public FeignFormatterRegistrar localDateTimeFormatRegister() {
//        return registry -> registry.addConverter(new LocalDateTime2StringConverter());
//    }
//
//    @Bean
//    public FeignFormatterRegistrar localDateFormatRegister() {
//        return registry -> registry.addConverter(new LocalDate2StringConverter());
//    }
//
//    @Bean
//    public FeignFormatterRegistrar localTimeFormatRegister() {
//        return registry -> registry.addConverter(new LocalTime2StringConverter());
//    }
//
//    private static class Date2StringConverter implements Converter<Date, String> {
//        @Override
//        public String convert(@NonNull Date source) {
//            return FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss").format(source);
//        }
//    }
//
//    private static class LocalDateTime2StringConverter implements Converter<LocalDateTime, String> {
//        @Override
//        public String convert(@NonNull LocalDateTime source) {
//            return source.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//        }
//    }
//
//    private static class LocalDate2StringConverter implements Converter<LocalDate, String> {
//        @Override
//        public String convert(@NonNull LocalDate source) {
//            return source.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//        }
//    }
//
//    private static class LocalTime2StringConverter implements Converter<LocalTime, String> {
//        @Override
//        public String convert(@NonNull LocalTime source) {
//            return source.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
//        }
//    }
//
//    /**
//     * 添加info级别feign日志
//     */
//    @Bean
//    public feign.Logger FeignLog() {
//        return new FeignLogger();
//    }
//
//    public static class FeignLogger extends feign.Logger {
//
//        private Logger logger;
//
//        public FeignLogger() {
//            this(FeignLogger.class);
//        }
//
//        public FeignLogger(Class<?> clazz) {
//            this(LoggerFactory.getLogger(clazz));
//        }
//
//        FeignLogger(Logger logger) {
//            this.logger = logger;
//        }
//
//        @Override
//        protected void logRequest(String configKey, Level logLevel, Request request) {
//            super.logRequest(configKey, logLevel, request);
//            log(configKey, "%s: %s", "requestHeader", FastJsonUtil.toJSONString(request.headers()));
//            int bodyLength = 0;
//            if (request.body() != null) {
//                bodyLength = request.length();
//                String bodyText = request.charset() != null ? new String(request.body(), request.charset())
//                        : null;
//                log(configKey, ""); // CRLF
//                log(configKey, "%s", bodyText != null ? bodyText : "Binary data");
//            }
//            log(configKey, "---> END HTTP (%s-byte body)", bodyLength);
//        }
//
//        @Override
//        protected Response logAndRebufferResponse(String configKey, Level logLevel, Response response, long elapsedTime)
//                throws IOException {
//            if (logger.isInfoEnabled()) {
//                return super.logAndRebufferResponse(configKey, logLevel, response, elapsedTime);
//            }
//            return response;
//        }
//
//        @Override
//        protected void log(String configKey, String format, Object... args) {
//            if (logger.isInfoEnabled()) {
//                logger.info(String.format(methodTag(configKey) + format, args));
//            }
//        }
//    }
//}
