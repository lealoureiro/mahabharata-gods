package org.leandroloureiro.mahabharatagods.services;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.BDDAssertions.then;

class MahabharataDataSourceImplHTTPMockTest {

    private MahabharataDataSource service = new MahabharataDataSourceImpl(new RestTemplate(), "localhost:8090");

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
    void testGetMahabharataBook() {

        wireMockServer.stubFor(get(urlEqualTo("/stream/TheMahabharataOfKrishna-dwaipayanaVyasa/MahabharataOfVyasa-EnglishTranslationByKMGanguli_djvu.txt"))
                .willReturn(aResponse().withHeader("Content-Type", "text/plain")
                        .withStatus(200)
                        .withBodyFile("MahabharataOfVyasa-EnglishTranslationByKMGanguli_djvu.txt")));


        final var godList = service.getMahabharataBook();

        final var result = godList.join();

        then(result).isEqualTo("BookContent\n");


    }

}
