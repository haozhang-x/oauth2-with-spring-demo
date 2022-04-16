package com.example.demo.client.feign.interceptor;

import com.example.demo.client.token.GetOAuth2AccessTokenService;
import feign.Logger;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OAuth2FeignRequestInterceptor implements RequestInterceptor {
    private static final String AUTHORIZATION_HEADER = "Authorization";

    private static final String BEARER_TOKEN_TYPE = "Bearer";

    private final GetOAuth2AccessTokenService getOAuth2AccessTokenService;

    public OAuth2FeignRequestInterceptor(GetOAuth2AccessTokenService getOAuth2AccessTokenService) {
        this.getOAuth2AccessTokenService = getOAuth2AccessTokenService;
    }


    @Override
    public void apply(RequestTemplate template) {
        template.header(AUTHORIZATION_HEADER,
                String.format("%s %s",
                        BEARER_TOKEN_TYPE,
                        getOAuth2AccessTokenService.getOAuth2AuthorizedClient().getAccessToken().getTokenValue()));
    }


    @Bean
    Logger.Level feignLoggerLevel(){
        return Logger.Level.FULL;
    }
}
