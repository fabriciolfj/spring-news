package com.github.fabrluc.practicespring.configuration;

import com.github.fabrluc.practicespring.service.JsonPlaceholderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class JsonPlaceholderClientConfig {

    private final String url;

    public JsonPlaceholderClientConfig(@Value("${jsonplace.url}") final String url) {
        this.url = url;
    }

    @Bean
    public JsonPlaceholderService jsonPlaceholderService() {
        var restClient = RestClient.create(url);
        var factory = HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient)).build();

        return factory.createClient(JsonPlaceholderService.class);
    }
}
