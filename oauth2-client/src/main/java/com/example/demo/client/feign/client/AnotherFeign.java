package com.example.demo.client.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "AnotherFeign", url = "https://api.uomg.com/api")
public interface AnotherFeign {

    /**
     * 访问资源服务器上的资源
     */
    @PostMapping("/visitor.info?skey=774740085")
    String visitor();

}
