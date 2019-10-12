package org.leandroloureiro.mahabharatagods.services;

import io.vavr.control.Either;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

/**
 * Check if Indian God is valid against Wikipedia
 */
public class IndianGodServiceImpl implements IndianGodService {

    private static final Logger LOG = LoggerFactory.getLogger(IndianGodServiceImpl.class);

    private final RestTemplate restTemplate;
    private final Executor apiCallExecutor;
    private final String hostname;

    IndianGodServiceImpl(final RestTemplate restTemplate, final Executor apiCallExecutor, final String hostname) {
        this.restTemplate = restTemplate;
        this.apiCallExecutor = apiCallExecutor;
        this.hostname = hostname;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CompletableFuture<Boolean> isValidIndianGod(final String indianGod) {
        return CompletableFuture.supplyAsync(() -> {

            final var uri = getURI(hostname, indianGod);

            if (uri.isRight()) {

                restTemplate.getForObject(uri.get(), String.class);

                return true;

            } else {

                return false;

            }

        }, apiCallExecutor).handle((__, e) -> Objects.isNull(e));
    }

    private static Either<String, URI> getURI(final String address, final String indianGod) {

        try {

            final var baseUrl = "http://" + address + "/wiki/" + indianGod;

            return Either.right(new URI(baseUrl));

        } catch (final URISyntaxException e) {

            LOG.error("Failed to create resource URL", e);

            return Either.left("Wikipedia Indian God resource URI invalid!");

        }
    }
}
