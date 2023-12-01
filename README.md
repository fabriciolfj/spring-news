# spring-news

## jdbcclient
- é um wrapper sobre o jdbctemplate, para executar operações no banco de dados
- ele abstrai a questão ro rowMapper e dos nomes das propriedades do bean, substituindo cameCase por anotação sublinhado.

## Paginacao
```
curl --location 'http://localhost:8080/products?page=0&size=50&sort=price,DESC'
```

## Novo rest client
- exemplo seu uso de interfaces
```
RestClient restClient = RestClient.create();

String result = restClient.get()
  .uri("https://example.com")
  .retrieve()
  .body(String.class);
System.out.println(result);
```
- com uso das interfaces, simulando um openfeign
```
public class JsonPlaceholderClientConfig {

    private final String url;

    public JsonPlaceholderClientConfig(@Value("${jsonplace.url}") final String url) {
        this.url = url;
    }

    @Bean
    public JsonPlaceholderService jsonPlaceholderService() {
        final RestClient restClient = RestClient.create(this.url);
        final var factory = HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient)).build();

        return factory.createClient(JsonPlaceholderService.class);
    }
}

public class JsonPlaceholderClientConfig {

    private final String url;

    public JsonPlaceholderClientConfig(@Value("${jsonplace.url}") final String url) {
        this.url = url;
    }

    @Bean
    public JsonPlaceholderService jsonPlaceholderService() {
        final RestClient restClient = RestClient.create(this.url);
        final var factory = HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient)).build();

        return factory.createClient(JsonPlaceholderService.class);
    }
}


```

# Anotação conditionalOnThreading
- permite a criação de um bean com base no tipo de thread, seja de plataforma (comum)
- ou threads virtuais
- para isso deixamos a propriedade abaixo habilitada e anotação no bean @ConditionalOnThreading(Threading.tipo PLATAFORM/VIRTUAL)
```
spring.threads.virtual.enabled=true
```