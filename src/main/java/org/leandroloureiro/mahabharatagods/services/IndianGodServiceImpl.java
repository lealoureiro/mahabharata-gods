package org.leandroloureiro.mahabharatagods.services;

import io.vavr.control.Either;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

/**
 * Check if Indian God is valid against Wikipedia
 */
@Service
public class IndianGodServiceImpl implements IndianGodService {

    private static final Logger LOG = LoggerFactory.getLogger(IndianGodServiceImpl.class);

    private final RestTemplate restTemplate;
    private final Executor apiCallExecutor;
    private final String indianGodServiceHostname;

    IndianGodServiceImpl(final RestTemplate restTemplate,
                         final Executor apiCallExecutor,
                         final String indianGodServiceHostname) {
        this.restTemplate = restTemplate;
        this.apiCallExecutor = apiCallExecutor;
        this.indianGodServiceHostname = indianGodServiceHostname;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CompletableFuture<Boolean> isValidIndianGod(final String indianGod) {
        return CompletableFuture.supplyAsync(() -> {

            LOG.info("Checking the validity of God {}.", indianGod);

            final var uri = getURI(indianGodServiceHostname, indianGod);

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

            final var baseUrl = "http://" + address + "/wiki/" + URLEncoder.encode(indianGod, StandardCharsets.UTF_8);

            return Either.right(new URI(baseUrl));

        } catch (final URISyntaxException e) {

            LOG.error("Failed to create resource URL", e);

            return Either.left("Wikipedia Indian God resource URI invalid!");

        }
    }
}
