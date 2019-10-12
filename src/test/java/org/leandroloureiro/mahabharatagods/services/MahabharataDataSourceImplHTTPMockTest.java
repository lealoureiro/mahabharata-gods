package org.leandroloureiro.mahabharatagods.services;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Executors;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.BDDAssertions.then;

class MahabharataDataSourceImplHTTPMockTest {

    private MahabharataDataSource service;

    private WireMockServer wireMockServer;

    @BeforeEach
    void setup() {

        wireMockServer = new WireMockServer(8090);
        wireMockServer.start();

        final var executor = Executors.newSingleThreadExecutor();

        service = new MahabharataDataSourceImpl(new RestTemplate(), executor, "localhost:8090");

    }

    @AfterEach
    void tearDown() {
        wireMockServer.stop();
    }

    @Test
    void testGetMahabharataBook() {

        wireMockServer.stubFor(get(urlEqualTo("/stream/TheMahabharataOfKrishna-dwaipayanaVyasa/MahabharataOfVyasa-EnglishTranslationByKMGanguli_djvu.txt"))
                .willReturn(aResponse().withHeader("Content-Type", "text/plain")
                        .withStatus(200)
                        .withBodyFile("MahabharataOfVyasa-EnglishTranslationByKMGanguli_djvu.txt")));


        final var godList = service.getMahabharataBook();

        final String result = godList.join();

        then(result).startsWith("Mahabharata");

    }

}
