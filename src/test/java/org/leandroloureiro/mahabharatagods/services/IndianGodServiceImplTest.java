package org.leandroloureiro.mahabharatagods.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.concurrent.Executors;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IndianGodServiceImplTest {


    private RestTemplate restTemplate;

    private IndianGodServiceImpl service;


    @BeforeEach
    void setup() {

        restTemplate = mock(RestTemplate.class);

        final var executor = Executors.newSingleThreadExecutor();

        service = new IndianGodServiceImpl(restTemplate, executor, "localhost:8080");

    }

    @Test
    void testValidIndiaGod() throws Exception {

        var uri = new URI("http://localhost:8080/wiki/God1");

        when(restTemplate.getForObject(uri, String.class)).thenReturn("");

        final var result = service.isValidIndianGod("God1");

        final var valid = result.join();

        then(valid).isTrue();

    }

    @Test
    void testInvalidGod() throws Exception {

        var uri = new URI("http://localhost:8080/wiki/God1");

        doThrow(new RestClientException("404 - Not found!")).when(restTemplate).getForObject(uri, String.class);

        final var result = service.isValidIndianGod("God1");

        final var valid = result.join();

        then(valid).isFalse();

    }

}
