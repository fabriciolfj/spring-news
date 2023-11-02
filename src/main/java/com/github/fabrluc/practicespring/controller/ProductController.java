package com.github.fabrluc.practicespring.controller;


/*
@RestController
@RequiredArgsConstructor
@RequestMapping("/products/api/v1")
public class ProductController {

    private final ProductRepository productRepository;

    private final static Random RANDOM = new Random();

    @PostMapping
    @Transactional
    public Mono<Void> populate() {
        return Flux.range(0, 1000)
                .map(c -> new Product(null, "Test " + c, BigDecimal.valueOf(RANDOM.nextDouble())))
                .flatMap(productRepository::save)
                .then();
    }

    @GetMapping
    public Mono<Page<Product>> findAllProducts(final Pageable pageable) {
        return this.productRepository.findAllBy(pageable)
                .collectList()
                .zipWith(productRepository.count())
                .map(c -> new PageImpl<>(c.getT1(), pageable, c.getT2()));
    }
}
*/