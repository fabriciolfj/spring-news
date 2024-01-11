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