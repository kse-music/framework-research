package cn.hiboot.framework.research.spring.http;

import cn.hiboot.framework.research.spring.basic.UserBean;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class RestTemplateDemo {

    private static final String BASE_PATH = "http://localhost:8080/api/";

    private RestTemplate restTemplate;

    @BeforeEach
    public void init() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(60000);
        factory.setConnectTimeout(60000);
        restTemplate = new RestTemplate(factory);
        restTemplate.getInterceptors().add(new HttpInterceptor());
    }

    static class RestResp<T>{
        private T data;

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }
    }

    @Test
    public void getForObject(){
        String s = restTemplate.getForObject(BASE_PATH+"test/list",String.class);
        RestResp restResp = restTemplate.getForObject(BASE_PATH+"test/list",RestResp.class);
        log.info("Result = {}",s);
    }

    @Test
    public void getForEntity(){
        ResponseEntity<String> s = restTemplate.getForEntity(BASE_PATH+"test/list",String.class);
        log.info("Result = {}",s.getStatusCodeValue());
        log.info("Result = {}",s.getHeaders());
        log.info("Result = {}",s.getBody());
    }

    @Test
    public void postForObject(){
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        body.add("bean",timestamp);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);

        String s = restTemplate.postForObject(BASE_PATH+"test/add", requestEntity, String.class);
        log.info(s);

    }

    @Test
    public void postForEntity(){
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        body.add("bean",timestamp);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> s = restTemplate.postForEntity(BASE_PATH+"test/add", requestEntity, String.class);
        log.info("Result = {}",s);

    }

    @Test
    public void restTemplate(){
        UserBean userBean = new UserBean();
        userBean.setName("restTemplate");
        String obj = restTemplate.postForObject("http://192.168.1.119:8080/test/json", userBean, String.class);
        log.info("Result = {}",obj);

    }

    @Test
    public void restTemplateGeneric(){
        UserBean userBean = new UserBean();
        userBean.setName("restTemplate");
        ResponseEntity<RestResp<UserBean>> exchange = restTemplate.exchange("http://192.168.1.119:8080/test/json", HttpMethod.POST, new HttpEntity<>(userBean), new ParameterizedTypeReference<RestResp<UserBean>>() {
        });
        RestResp<UserBean> body = exchange.getBody();
        log.info("Result = {}",body.getData());

    }

    @Slf4j
    static class HttpInterceptor implements ClientHttpRequestInterceptor {

        @Override
        public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
            log.info("请求地址：{}", request.getURI());
            log.info("请求方法： {}", request.getMethod());
            log.info("请求内容：{}", new String(body));
            log.info("请求头：{}", request.getHeaders());
            return execution.execute(request, body);
        }
    }

}
