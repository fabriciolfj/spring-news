# spring-news
## mudando o level de logs em tempo de execução:
```
curl --location 'localhost:8080/actuator/loggers/com.github.fabriciolfj.orders' \
--header 'Content-Type: application/json' \
--data '{
    "configuredLevel" : "DEBUG"
}'
```
spring:
  security:
    oauth2:
      client:
        registration:
          messaging-client:
            provider: spring
            client-id: client1
            client-secret: my-secret
            authorization-grant-type: authorization_code
            scope: message.read,message.write
        provider:
          spring:
            issuer-uri: http://localhost:9000
            
@Configuration
public class RestClientConfig {

	@Bean
	public RestClient restClient(RestClient.Builder builder, OAuth2AuthorizedClientManager authorizedClientManager) {
		OAuth2ClientHttpRequestInterceptor requestInterceptor =
			new OAuth2ClientHttpRequestInterceptor(authorizedClientManager);

		return builder.requestInterceptor(requestInterceptor).build();
	}

}
```
- para uma configuração personalizada para cada grant_type
```
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final RestClient restClient;

	@PostConstruct
	void initialize() {
		this.restClient = RestClient.builder()
			.messageConverters((messageConverters) -> {
				messageConverters.clear();
				messageConverters.add(new FormHttpMessageConverter());
				messageConverters.add(new OAuth2AccessTokenResponseHttpMessageConverter());
			})
			.defaultStatusHandler(new OAuth2ErrorResponseErrorHandler())
			// TODO: Customize the instance of RestClient as needed...
			.build();
	}

	@Bean
	public OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> authorizationCodeAccessTokenResponseClient() {
		RestClientAuthorizationCodeTokenResponseClient accessTokenResponseClient =
			new RestClientAuthorizationCodeTokenResponseClient();
		accessTokenResponseClient.setRestClient(this.restClient);

		return accessTokenResponseClient;
	}

	@Bean
	public OAuth2AccessTokenResponseClient<OAuth2RefreshTokenGrantRequest> refreshTokenAccessTokenResponseClient() {
		RestClientRefreshTokenTokenResponseClient accessTokenResponseClient =
			new RestClientRefreshTokenTokenResponseClient();
		accessTokenResponseClient.setRestClient(this.restClient);

		return accessTokenResponseClient;
	}

	@Bean
	public OAuth2AccessTokenResponseClient<OAuth2ClientCredentialsGrantRequest> clientCredentialsAccessTokenResponseClient() {
		RestClientClientCredentialsTokenResponseClient accessTokenResponseClient =
			new RestClientClientCredentialsTokenResponseClient();
		accessTokenResponseClient.setRestClient(this.restClient);

		return accessTokenResponseClient;
	}

	@Bean
	public OAuth2AccessTokenResponseClient<OAuth2PasswordGrantRequest> passwordAccessTokenResponseClient() {
		return (grantRequest) -> {
			throw new UnsupportedOperationException("The `password` grant type is not supported.");
		};
	}

	@Bean
	public OAuth2AccessTokenResponseClient<JwtBearerGrantRequest> jwtBearerAccessTokenResponseClient() {
		RestClientJwtBearerTokenResponseClient accessTokenResponseClient =
			new RestClientJwtBearerTokenResponseClient();
		accessTokenResponseClient.setRestClient(this.restClient);

		return accessTokenResponseClient;
	}

	@Bean
	public OAuth2AccessTokenResponseClient<TokenExchangeGrantRequest> tokenExchangeAccessTokenResponseClient() {
		RestClientTokenExchangeTokenResponseClient accessTokenResponseClient =
			new RestClientTokenExchangeTokenResponseClient();
		accessTokenResponseClient.setRestClient(this.restClient);

		return accessTokenResponseClient;
	}

}
```

## Spring resources server
```
spring:
  security:
    oauth2:
      resourceserver:
        jwt:
          audiences:
          - football -> pra quem se destina o token
          - football-ui
          issuer-uri: http://localhost:9000 -> baixar as chaves jwks (http://localhost:9000/.well-known/openid-configuration), para validar o token. obs: baixa apenas uma vez e depois utiliza esses dados em memória.
```
## Spring authorization manager
- para verificar os endpotins expostos pelo spring authorization manager:
```
http://localhost:9000/.well-known/openid-configuration
```
- exemplo de configuração de dois clients via application yml
```
server:
  port: 9000

logging:
  level:
    org.springframework.security: trace

spring:
  security:
    user:
      name: "user"
      password: "password"
    oauth2:
      authorizationserver:
        client:            
          football:
            registration:
              client-id: "football"
              client-secret: "{noop}SuperSecret"
              client-authentication-methods:
                - "client_secret_post"
              authorization-grant-types:
                - "client_credentials"
              scopes:
                - "football:read"
                - "football:admin"
          football-ui:
            registration:
              client-id: "football-ui"
              client-secret: "{noop}TheSecretSauce"
              client-authentication-methods:
                - "client_secret_basic"
              authorization-grant-types:
                - "authorization_code"
                - "refresh_token"
                - "client_credentials"
              redirect-uris: 
                - "http://localhost:9080/login/oauth2/code/football-ui"
              scopes:
                - "openid"
                - "profile"
                - "football:read"
                - "football:admin"
            require-authorization-consent: true
```
- exemplo para requisitar um token
```
curl --location 'http://localhost:9000/oauth2/token' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'grant_type=client_credentials' \
--data-urlencode 'client_id=football' \
--data-urlencode 'client_secret=SuperSecret' \
--data-urlencode 'scope=football:read'
```

## features deste projeto
- controle dos listeners kafka

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

# Spel com spring data
- podemos utilizar expressões regulares dentro das query, em nossos repositorios
- podemos utiliza o EvaluationContextExtension, para trazer recursos do spring as nossas spel, como por exemplo:
```
@Component
public class LocaleContextHolderExtension implements EvaluationContextExtension {

    @Override
    public String getExtensionId() {
        return "locale";
    }

    @Override
    public Locale getRootObject() {
        return LocaleContextHolder.getLocale();
    }
}

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("locale");
        registry.addInterceptor(localeChangeInterceptor);
    }
}

@Query(value = "SELECT * FROM articles WHERE language = :#{locale.language}", nativeQuery = true)
List<Article> findAllArticlesUsingLocaleWithNativeQuery();
```
- usar consultas dinamicas em tabelas, como:
```
@NoRepositoryBean
public interface BaseNewsApplicationRepository<T, ID> extends JpaRepository<T, ID> {
    @Query(value = "select e from #{#entityName} e")
    List<Article> findAllEntitiesUsingEntityPlaceholder();

    @Query(value = "SELECT * FROM #{#entityName}", nativeQuery = true)
    List<Article> findAllEntitiesUsingEntityPlaceholderWithNativeQuery();
}

@Entity(name = "articles")
@Table(name = "articles")
public class Article {
// ...
}

```

# findById  e getReferenceById
- A principal diferença entre findById() e getReferenceById() é quando eles carregam as entidades no contexto de persistência
- o findById ja interage com a base de dados, ja o getReferenceById vai interagir quando formos utilziar (ele é lazy)
- operações lazy fora de um persistence context (@transaction, ou seja desanexado), quando utilizar teremos uma exceção
- então operações lazy, precisam estar em um estado gerenciado, ou seja, dentro de uma transação.

# rastreabilidade spring, kafka micrometer 
- o kafka vem com integração com micrometer para kafkatemplate e seus listeners
- para iniciar precisa-se das dependências abaixo (actuator, ponte para o micrometer e o exportador):
```
<dependencies>
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter</artifactId>
  </dependency>
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
  </dependency>
  <dependency>
    <groupId>org.springframework.kafka</groupId>
    <artifactId>spring-kafka</artifactId>
  </dependency>
  <dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
  </dependency>
  <dependency>
    <groupId>io.micrometer</groupId>
    <artifactId>micrometer-tracing-bridge-otel</artifactId>
  </dependency>
  <dependency>
    <groupId>io.opentelemetry</groupId>
    <artifactId>opentelemetry-exporter-otlp</artifactId>
  </dependency>
</dependencies>
```
- para habilitar a observabilidade no produtor, temos que personalizar o bean kafkatemplate, alem de adicionar novas tags ao micrometer (ele coloca umas genéricas)
- deste exemplo abaixo, pegamos o nome do topic e a chave da mensagem
```
    @Bean
    public KafkaTemplate<Long, Info> kafkaTemplate(ProducerFactory<Long, Info> producerFactory) {
        KafkaTemplate<Long, Info> t = new KafkaTemplate<>(producerFactory);
        t.setObservationEnabled(true);
        t.setObservationConvention(new KafkaTemplateObservationConvention() {
            @Override
            public KeyValues getLowCardinalityKeyValues(KafkaRecordSenderContext context) {
                return KeyValues.of("topic", context.getDestination(),
                        "id", String.valueOf(context.getRecord().key()));
            }
        });
        return t;
    }
```
- e tambem no application.yaml, o percenaul de spans que será exportada
```
management:
  tracing:
    enabled: true
    sampling:
      probability: 1.0
  otlp:
    tracing:
      endpoint: http://jaeger:4318/v1/traces
```
- do lado do consumidor, devemos personalizar o container factory, habilizando a observationEnabled(true)
```
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> listenerFactory(ConsumerFactory<String, String> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.getContainerProperties().setObservationEnabled(true);
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }
```
- e definir o endereço do jaeger e o percentual de spans exportados (igual ao application.yaml ao produtor)
- finalizar um exemplo de um docker-compose-example na app
- para k8s, precisa instalar o jeager (podemos usar o helm)
```
helm repo add jaegertracing https://jaegertracing.github.io/helm-charts
values jaeger.yml
collector:
  service:
    otlp:
      grpc:
        name: otlp-grpc
        port: 4317
      http:
        name: otlp-http
        port: 4318

helm install jaeger jaegertracing/jaeger -n jaeger \
    --create-namespace \
    -f jaeger-values.yaml        
```

# spring kafka contexto de mensagens duplicadas e perda de mensagens
- ao utilizar o spring com kafka, por default o auto-commit e desabilitado, ou seja, o contexto do spring e responsável e não o kafka por confirmar as mensagens.
```
spring.kafka.enable.auto.commit=false
```
- outro ponto o modo de confirmação por default e batch
```
spring.kafka.listener.ack-mode=BATCH
```
- um exemplo simples de propriedades do produtor e consumidor
```
spring:
  application.name: producer
  kafka:
    bootstrap-servers: ${KAFKA_URL}
    producer:
      key-serializer: org.apache.kafka.common.serialization.LongSerializer
      value-serializer: org.springframework.kafka.support.serializer.JsonSerializer
```
```
spring:
  application.name: consumer
  output.ansi.enabled: ALWAYS
  kafka:
    bootstrap-servers: ${KAFKA_URL:localhost}:9092
    consumer:
      key-deserializer: org.apache.kafka.common.serialization.LongDeserializer
      value-deserializer: org.springframework.kafka.support.serializer.JsonDeserializer
      properties:
        spring.json.trusted.packages: "*"

logging.level:
  org.springframework.kafka: debug
```
- caso queria confirmar o consumo por mensagem, devemos ativar o listener ack-mode para RECORD
- outro ponto importante, podemos confirmar as mensagens quando o desligamento da app acontecer, mudando para:
```
spring.kafka.listener.immediate-stop
```
- caso delegamos o processamento para outra thread, a thread que recebeu a mensagem ja a confirma, mas caso a outra que esteja processando falhe, perdemos a mensagem
- ideal que confirmamos manualemente as mensagens quando delegamos para outra thread processar a mensagem.\
```
spring.kafka.listener.ack-mode: MANUAL_IMMEDIATE
```

# Exemplo de configuração ssl no spring 3
````
spring.ssl.bundle.jks:
  server:
    reload-on-update: true
    keystore:
      location: classpath:keystore.jks
      password: 123456
      type: JKS
    truststore:
      location: classpath:truststore.jks
      password: 123456
      type: JKS
````

# ReplyingKafkaTemplate
```
O ReplyingKafkaTemplate faz parte da versão mais recente da biblioteca Spring Kafka 
e é usado para implementar o pattern Request-Reply (solicitação-resposta) em aplicações Kafka.
Já o pattern Request-Reply é um tipo de pattern de mensagens ponto a 
ponto. Um produtor manda uma mensagem para um consumidor e aguarda uma resposta dentro de um
 tempo limite. O ReplyingKafkaTemplate é construído de modo a cumprir essa função.
```
