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
import java.util.concurrent.Executor;

/**
 * {@inheritDoc}
 */
@Service
public class MahabharataDataSourceImpl implements MahabharataDataSource {

    private static final Logger LOG = LoggerFactory.getLogger(MahabharataDataSourceImpl.class);

    private final RestTemplate restTemplate;
    private final Executor apiCallExecutor;
    private final String mahabharataDataSourceHostname;

    public MahabharataDataSourceImpl(final RestTemplate restTemplate,
                                     final Executor apiCallExecutor,
                                     final String mahabharataDataSourceHostname) {
        this.restTemplate = restTemplate;
        this.apiCallExecutor = apiCallExecutor;
        this.mahabharataDataSourceHostname = mahabharataDataSourceHostname;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CompletableFuture<String> getMahabharataBook() {

        return CompletableFuture.supplyAsync(() -> {

            final var uri = getURI(mahabharataDataSourceHostname);

            if (uri.isRight()) {

                return restTemplate.getForObject(uri.get(), String.class);

            } else {

                return StringUtils.EMPTY;

            }

        }, apiCallExecutor).handle((response, e) -> {
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
