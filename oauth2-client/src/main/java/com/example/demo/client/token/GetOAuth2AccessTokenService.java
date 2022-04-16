package com.example.demo.client.token;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetOAuth2AccessTokenService {
    private final OAuth2AuthorizedClientManager authorizedClientManager;

    public OAuth2AuthorizedClient getOAuth2AuthorizedClient() {
        OAuth2AuthorizeRequest request = OAuth2AuthorizeRequest
                .withClientRegistrationId("messaging-client-client-credentials")
                .principal("messaging-client-client-credentials")
                .build();
        return Optional.ofNullable(authorizedClientManager)
                .map(authorizedClientManager->authorizedClientManager.authorize(request))
                .orElseThrow(()->new RuntimeException("getOAuth2AuthorizedClient failed"));
    }
}
