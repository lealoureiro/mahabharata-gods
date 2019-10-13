package org.leandroloureiro.mahabharatagods.logic;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.BDDAssertions.then;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class TopMahabharataGodsImplTest {

    private WireMockServer wireMockServer;

    @Autowired
    private TopMahabharataGodsImpl component;


    @BeforeEach
    void setup() {
        wireMockServer = new WireMockServer(8090);
        wireMockServer.start();
    }

    @Test
    void testGetTopGods() {

        wireMockServer.stubFor(get(urlEqualTo("/jabrena/latency-problems/indian"))
                .willReturn(aResponse().withHeader("Content-Type", "application/json")
                        .withStatus(200)
                        .withBodyFile("indian.json")
                        .withUniformRandomDelay(10, 5000)));

        wireMockServer.stubFor(get(urlEqualTo("/wiki/Brahma"))
                .willReturn(aResponse().withHeader("text/html", "application/json")
                        .withStatus(200)
                        .withUniformRandomDelay(10, 5000)));

        wireMockServer.stubFor(get(urlEqualTo("/wiki/Rama"))
                .willReturn(aResponse().withHeader("text/html", "application/json")
                        .withStatus(200)
                        .withUniformRandomDelay(10, 5000)));

        wireMockServer.stubFor(get(urlEqualTo("/wiki/Hanuman"))
                .willReturn(aResponse().withHeader("text/html", "application/json")
                        .withStatus(200)
                        .withUniformRandomDelay(10, 5000)));

        wireMockServer.stubFor(get(urlEqualTo("/wiki/Lakshmi"))
                .willReturn(aResponse().withHeader("text/html", "application/json")
                        .withStatus(200)
                        .withUniformRandomDelay(10, 5000)));

        wireMockServer.stubFor(get(urlEqualTo("/wiki/Shiva"))
                .willReturn(aResponse().withHeader("text/html", "application/json")
                        .withStatus(200)
                        .withUniformRandomDelay(10, 5000)));

        wireMockServer.stubFor(get(urlEqualTo("/stream/TheMahabharataOfKrishna-dwaipayanaVyasa/MahabharataOfVyasa-EnglishTranslationByKMGanguli_djvu.txt"))
                .willReturn(aResponse().withHeader("Content-Type", "text/plain")
                        .withStatus(200)
                        .withBodyFile("MahabharataOfVyasa-EnglishTranslationByKMGanguli_djvu.txt")
                        .withUniformRandomDelay(10, 5000)));

        then(component).isNotNull();

        final var gods = component.getTopMahabharataGods();

        then(gods).isNotNull();
        then(gods.size()).isEqualTo(3);

        then(gods.get(0).getName()).isEqualTo("Brahma");
        then(gods.get(0).getHitCount()).isEqualTo(8100);

        then(gods.get(1).getName()).isEqualTo("Rama");
        then(gods.get(1).getHitCount()).isEqualTo(845);

        then(gods.get(2).getName()).isEqualTo("Hanuman");
        then(gods.get(2).getHitCount()).isEqualTo(54);

    }

}