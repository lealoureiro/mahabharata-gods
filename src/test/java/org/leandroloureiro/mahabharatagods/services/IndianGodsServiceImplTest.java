package org.leandroloureiro.mahabharatagods.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static java.util.Arrays.asList;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IndianGodsServiceImplTest {

    private RestTemplate restTemplate;

    private IndianGodsServiceImpl service;

    @BeforeEach
    void setup() {

        restTemplate = mock(RestTemplate.class);

        service = new IndianGodsServiceImpl(restTemplate, "localhost:8080");

    }

    @Test
    void testGetIndianGods() throws Exception {

        var uri = new URI("http://localhost:8080/jabrena/latency-problems/indian");

        var response = ResponseEntity.ok(asList("God1", "God2"));

        final ParameterizedTypeReference<List<String>> type = new ParameterizedTypeReference<>() {};

        when(restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(type), type)).thenReturn(response);

        var godList = service.getGodList();

        var result = godList.join();

        then(result.isPresent()).isTrue();
        then(result.get()).isEqualTo(asList("God1", "God2"));

        verify(restTemplate).exchange(uri, HttpMethod.GET, new HttpEntity<>(type), type);

        verifyNoMoreInteractions(restTemplate);

    }


}