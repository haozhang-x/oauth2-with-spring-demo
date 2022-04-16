package com.example.demo.client.feign.client;

import com.example.demo.client.feign.interceptor.OAuth2FeignRequestInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "MessageResourceFeign", url = "http://localhost:8090/",
        configuration = OAuth2FeignRequestInterceptor.class)
public interface MessageResourceFeign {

    /**
     * 访问资源服务器上的资源
     */
    @GetMapping("/messages")
    String messages();

}
