package org.leandroloureiro.mahabharatagods.services;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.BDDAssertions.then;

class IndianGodServiceImplHTTPMockTest {

    private IndianGodService service;

    private WireMockServer wireMockServer;

    @BeforeEach
    void setup() {

        wireMockServer = new WireMockServer(8090);
        wireMockServer.start();

        final Executor executor = Executors.newSingleThreadExecutor();

        service = new IndianGodServiceImpl(new RestTemplate(), executor, "localhost:8090");

    }

    @AfterEach
    void tearDown() {
        wireMockServer.stop();
    }


    @Test
    void testValidIndianGod() {

        wireMockServer.stubFor(get(urlEqualTo("/wiki/Brahma"))
                .willReturn(aResponse().withHeader("text/html", "application/json")
                        .withStatus(200)));

        final var result = service.isValidIndianGod("Brahma");

        final var valid = result.join();

        then(valid).isTrue();

    }

    @Test
    void testInvalidIndianGod() {

        wireMockServer.stubFor(get(urlEqualTo("/wiki/Jesus"))
                .willReturn(aResponse().withHeader("text/html", "application/json")
                        .withStatus(404)));

        final var result = service.isValidIndianGod("Jesus");

        final var valid = result.join();

        then(valid).isFalse();

    }

}
