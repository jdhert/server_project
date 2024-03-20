package com.kitri.web_project.service;

import com.kitri.web_project.dto.api.ApiResponse;
import com.kitri.web_project.dto.api.DataItem;
import com.kitri.web_project.mybatis.mappers.ApiMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.xml.crypto.Data;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

@Service
public class ApiService {
    private final WebClient webClient;
//    private final Logger logger = Logger.getLogger(ApiService.class.getName());

//    private static final String serviceKey = "s2R60Aa%2BZ6BD0BTcH9dDSXbhLfcS63ozL8fJuc0gZ9D79b7i7GHuE6BYUq41Mulp5V%2Bi3%2FCEgGGUvv7S6cEJ9g%3D%3D";

    @Autowired
    private ApiMapper apiMapper;



    public void saveDataItems(List<DataItem> items) {
        for (DataItem item : items) {
            apiMapper.insertIt(item); // 각 DataItem 객체를 DB에 저장
        }
    }


    @Value("${api.serviceKey}")
    private String serviceKey;
    @Autowired
    public ApiService(WebClient.Builder webClientBuilder) {

        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory();
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);

        this.webClient = webClientBuilder
                .uriBuilderFactory(factory)
                .baseUrl("https://api.odcloud.kr/api/15111389/v1")
                .exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(configurer -> configurer.defaultCodecs()
                                .maxInMemorySize(512 * 1024 * 1024)) // 16MB로 설정
                        .build())
//                .filter(logRequest())
//                .filter(logResponse())
                .build();
    }

    public void fetchAndSaveData(int page, int perPage) {
        UriComponents uriComponents = UriComponentsBuilder
                .fromHttpUrl("https://api.odcloud.kr/api/15111389/v1/uddi:41944402-8249-4e45-9e9d-a52d0a7db1cc")
                .queryParam("page", page)
                .queryParam("perPage", perPage)
                .queryParam("serviceKey", serviceKey)
                .build(true);

        String url = uriComponents.toUriString();

        webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(ApiResponse.class)
                .flatMapMany(apiResponse -> Flux.fromIterable(apiResponse.getData()))
                .subscribe(this::saveDataItem);
    }

    private void saveDataItem(DataItem item) {
        apiMapper.insertIt(item);
    }

    public Mono<ApiResponse> getLocate(int page, int perPage) {
        UriComponents uriComponents = UriComponentsBuilder
                .fromHttpUrl("https://api.odcloud.kr/api/15111389/v1/uddi:41944402-8249-4e45-9e9d-a52d0a7db1cc")
                .queryParam("page", page)
                .queryParam("perPage", perPage)
                .queryParam("serviceKey", serviceKey)
                .build(true);  // 인코딩 활성화
        String url = uriComponents.toUriString();

        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(ApiResponse.class);
    }

    public List<DataItem> getLocate(double lat, double lon){
        return apiMapper.getNearBy(lat, lon);
    }
    public List<DataItem> getCategory(String category, int page){
        return apiMapper.getByCategory(category, (page-1) * 10);
    }

    public List<DataItem> getList(int page){
        return apiMapper.getList((page-1) * 10);
    }

//    public Mono<List<DataItem>> getCategoryItem(String input, int page, int perPage){
//
//    }

//    public Mono<List<DataItem>> getNearestLocations(double currentLat, double currentLon, int page, int perPage) {
//        return Flux.range(page, 10) // 1부터 시작하여 10 페이지까지
//                .flatMap(pageNumber -> getLocate(pageNumber, perPage).flatMapMany(apiResponse -> Flux.fromIterable(apiResponse.getData())))
//                .map(dataItem -> {
//                    double lat = Double.parseDouble(dataItem.get위도());
//                    double lon = Double.parseDouble(dataItem.get경도());
//                    double distance = calculateDistance(currentLat, currentLon, lat, lon);
//                    dataItem.setDistance(distance); // DataItem 클래스에 거리(distance) 필드를 추가하고 setter를 구현
//                    return dataItem;
//                })
//                .sort(Comparator.comparingDouble(DataItem::getDistance))
//                .take(10) // 가장 가까운 10개 항목만 가져옴
//                .collectList(); // 결과를 List로 수집





//        return getLocate(page, perPage)
//                .flatMapMany(apiResponse -> Mono.just(apiResponse.getData()))
//                .flatMapIterable(data -> data)
//                .map(dataItem -> {
//                    double lat = Double.parseDouble(dataItem.get위도());
//                    double lon = Double.parseDouble(dataItem.get경도());
//                    double distance = calculateDistance(currentLat, currentLon, lat, lon);
//                    dataItem.setDistance(distance); // DataItem 클래스에 거리(distance) 필드를 추가하고 setter를 구현해야 합니다.
//                    return dataItem;
//                })
//                .sort(Comparator.comparingDouble(DataItem::getDistance))
//                .take(10)
//                .collectList();
    }

//
//    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
//        final int R = 6371;
//
//        double latDistance = Math.toRadians(lat2 - lat1);
//        double lonDistance = Math.toRadians(lon2 - lon1);
//        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
//                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
//                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
//        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
//
//        return R * c;
//    }


//    private ExchangeFilterFunction logRequest() {
//        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
//            logger.info("Request: " + clientRequest.method() + " " + clientRequest.url());
//            clientRequest.headers().forEach((name, values) -> values.forEach(value -> logger.info(name + ": " + value)));
//            return Mono.just(clientRequest);
//        });
//    }
//
//
//    private ExchangeFilterFunction logResponse() {
//        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
//            logger.info("Response Status Code: " + clientResponse.statusCode());
//            return Mono.just(clientResponse);
//        });
//
//    }

//}

