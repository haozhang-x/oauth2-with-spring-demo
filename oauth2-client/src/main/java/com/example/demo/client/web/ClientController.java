package com.example.demo.client.web;

import com.example.demo.client.feign.client.MessageResourceFeign;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ClientController {
    private final MessageResourceFeign messageResourceFeign;

    /**
     * 通过Feign访问资源服务器上的资源
     */
    @GetMapping("/getMessages")
    public String getMessages(){
        return messageResourceFeign.messages();
    }

}
