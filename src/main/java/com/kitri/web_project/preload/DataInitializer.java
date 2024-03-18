package com.kitri.web_project.preload;

import com.kitri.web_project.dto.api.ApiResponse;
import com.kitri.web_project.dto.api.DataItem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import reactor.core.publisher.Flux;

//@Component
public class DataInitializer implements ApplicationRunner {

    private final WebClient webClient;
    private final DataService dataService;

    public DataInitializer(WebClient.Builder webClientBuilder, DataService dataService) {
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory();
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);

        this.webClient = webClientBuilder
                .uriBuilderFactory(factory)
                .baseUrl("https://api.odcloud.kr/api/15111389/v1")
                .exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(configurer -> configurer.defaultCodecs()
                                .maxInMemorySize(128 * 1024 * 1024)) // 16MB로 설정
                        .build())
                .build();
        this.dataService = dataService;
    }

    @Override
    public void run(ApplicationArguments args) {
        fetchDataAndStore();
    }


    @Value("${api.serviceKey}")
    private String serviceKey;

    private void fetchDataAndStore() {
        int totalPages = 30; // 총 페이지 수
        int perPage = 3; // 페이지 당 항목 수

        Flux.range(1, totalPages) // 1부터 시작하여 totalPages까지 반복
                .flatMap(page ->
                        webClient.get()
                                .uri(uriBuilder -> uriBuilder
                                        .path("/uddi:41944402-8249-4e45-9e9d-a52d0a7db1cc")
                                        .queryParam("page", page)
                                        .queryParam("perPage", perPage)
                                        .queryParam("serviceKey", serviceKey)
                                        .build())
                                .retrieve()
                                .bodyToFlux(ApiResponse.class))
                .collectList() // Flux<DataItem>을 List<DataItem>으로 수집
                .subscribe(dataService::initializeDataCache2,
                        error -> System.out.println("Error fetching data: " + error.getMessage()));
    }
}
