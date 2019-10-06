package org.leandroloureiro.mahabharatagods.services;

import io.vavr.control.Either;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * {@inheritDoc}
 */
@Service
public class MahabharataDataSourceImpl implements MahabharataDataSource {

    private static final Logger LOG = LoggerFactory.getLogger(MahabharataDataSourceImpl.class);

    private final RestTemplate restTemplate;
    private final String hostname;

    public MahabharataDataSourceImpl(final RestTemplate restTemplate, final String hostname) {
        this.restTemplate = restTemplate;
        this.hostname = hostname;
    }

    @Override
    public CompletableFuture<String> getMahabharataBook() {

        return CompletableFuture.supplyAsync(() -> {

            final var uri = getURI(hostname);

            if (uri.isRight()) {

                return restTemplate.getForObject(uri.get(), String.class);

            } else {

                return StringUtils.EMPTY;

            }

        }).handle((response, e) -> {
            if (Objects.isNull(e)) {
                return response;
            } else {
                LOG.error("Failed to get Mahabharata book.", e);
                return StringUtils.EMPTY;
            }
        });

    }

    private static Either<String, URI> getURI(final String address) {

        try {

            final var baseUrl = "http://" + address + "/stream/TheMahabharataOfKrishna-dwaipayanaVyasa/MahabharataOfVyasa-EnglishTranslationByKMGanguli_djvu.txt";

            return Either.right(new URI(baseUrl));

        } catch (final URISyntaxException e) {

            LOG.error("Failed to create resource URL", e);

            return Either.left("Mahabharata book resource URI invalid!");

        }
    }
}
