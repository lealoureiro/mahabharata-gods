package org.leandroloureiro.mahabharatagods.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.concurrent.Executors;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MahabharataDataSourceImplTest {


    private RestTemplate restTemplate;

    private MahabharataDataSource service;


    @BeforeEach
    void setup() {

        restTemplate = mock(RestTemplate.class);

        final var executor = Executors.newSingleThreadExecutor();

        service = new MahabharataDataSourceImpl(restTemplate, executor, "localhost:8080");

    }

    @Test
    void testGetMahabharataBook() throws Exception {

        final var uri = new URI("http://localhost:8080/stream/TheMahabharataOfKrishna-dwaipayanaVyasa/MahabharataOfVyasa-EnglishTranslationByKMGanguli_djvu.txt");

        when(restTemplate.getForObject(uri, String.class)).thenReturn("SomeBookContent");

        final var book = service.getMahabharataBook();

        final var bookContent = book.join();

        then(bookContent).isEqualTo("SomeBookContent");

        verify(restTemplate).getForObject(uri, String.class);

        verifyNoMoreInteractions(restTemplate);

    }

}