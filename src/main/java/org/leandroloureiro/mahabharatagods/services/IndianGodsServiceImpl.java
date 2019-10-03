package org.leandroloureiro.mahabharatagods.services;

import io.vavr.control.Either;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class IndianGodsServiceImpl implements IndianGodsService {

    private static final Logger LOG = LoggerFactory.getLogger(IndianGodsServiceImpl.class);

    private final RestTemplate client;
    private final String hostname;

    public IndianGodsServiceImpl(final RestTemplate client,
                                 final String hostname) {
        this.client = client;
        this.hostname = hostname;
    }

    @Override
    public CompletableFuture<Optional<List<String>>> getGodList() {

        return CompletableFuture.supplyAsync(() -> {

            final ParameterizedTypeReference<List<String>> type = new ParameterizedTypeReference<>() {};

            var either = getURI(hostname);

            if (either.isRight()) {
                var response = client.exchange(
                        either.get(),
                        HttpMethod.GET,
                        new HttpEntity<>(type),
                        type
                );
                return response.getBody();
            } else {
                return new ArrayList<String>();
            }

        }).handle((response, e) -> {
            if (Objects.isNull(e)) {
                if (response.isEmpty()) {
                    return Optional.empty();
                }
                return Optional.of(response);
            }
            LOG.warn(e.getLocalizedMessage(), e);
            return Optional.empty();
        });
    }

    private Either<String, URI> getURI(final String address) {

        try {

            var baseUrl = "http://" + address + "/jabrena/latency-problems/indian";

            return Either.right(new URI(baseUrl));

        } catch (final URISyntaxException e) {

            LOG.error("Failed to create resource URL", e);

            return Either.left("Indian Gods resource URI invalid!");

        }
    }

}
