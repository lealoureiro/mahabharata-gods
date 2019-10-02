package org.leandroloureiro.mahabharatagods.services;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static java.util.Arrays.asList;
import static org.assertj.core.api.BDDAssertions.then;

@ExtendWith(MockitoExtension.class)
class IndianGodsServiceImplHTTPMockTest {

    private IndianGodsServiceImpl service = new IndianGodsServiceImpl(new RestTemplate(), "localhost:8090");

    private WireMockServer wireMockServer;

    @BeforeEach
    void setup() {
        wireMockServer = new WireMockServer(8090);
        wireMockServer.start();
    }

    @AfterEach
    void tearDown() {
        wireMockServer.stop();
    }

    @Test
    void testGetIndianGods() {

        wireMockServer.stubFor(get(urlEqualTo("/jabrena/latency-problems/indian"))
                .willReturn(aResponse().withHeader("Content-Type", "application/json")
                        .withStatus(200)
                        .withBodyFile("indian.json")));


        var  godList = service.getGodList();

        var result = godList.join();

        then(result.isPresent()).isTrue();

        then(result.get()).isEqualTo(asList(
                "Brahma",
                "Vishnu",
                "Shiva",
                "Ganapati",
                "Rama",
                "Krishna",
                "Saraswati",
                "Lakshmi",
                "Durga Devi",
                "Indra",
                "Surya",
                "Agni",
                "Hanuman")
        );

    }

}