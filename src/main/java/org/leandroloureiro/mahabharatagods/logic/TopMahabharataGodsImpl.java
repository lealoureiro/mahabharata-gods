package org.leandroloureiro.mahabharatagods.logic;

import org.apache.commons.lang3.StringUtils;
import org.leandroloureiro.mahabharatagods.model.God;
import org.leandroloureiro.mahabharatagods.services.IndianGodService;
import org.leandroloureiro.mahabharatagods.services.IndianGodsService;
import org.leandroloureiro.mahabharatagods.services.MahabharataDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * {@inheritDoc}
 */
@Service
public class TopMahabharataGodsImpl implements TopMahabharataGods {

    private static final Logger LOG = LoggerFactory.getLogger(TopMahabharataGodsImpl.class);

    private final IndianGodsService indianGodsService;
    private final IndianGodService indianGodService;
    private final MahabharataDataSource mahabharataDataSource;

    public TopMahabharataGodsImpl(final IndianGodsService indianGodsService,
                                  final IndianGodService indianGodService,
                                  final MahabharataDataSource mahabharataDataSource) {
        this.indianGodsService = indianGodsService;
        this.indianGodService = indianGodService;
        this.mahabharataDataSource = mahabharataDataSource;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<God> getTopMahabharataGods() {

        final var mahabharataContent = mahabharataDataSource.getMahabharataBook();
        final var indianGods = indianGodsService.getGodList();

        return mahabharataContent.thenCombine(indianGods, (mahabharata, maybeGods) -> maybeGods.map(
                gods -> checkGodsAndCount(gods, mahabharata)
                        .stream()
                        .map(CompletableFuture::join)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .sorted(Comparator.comparingLong(God::getHitCount).reversed())
                        .limit(3)
                        .collect(Collectors.toUnmodifiableList())
                ).orElse(Collections.emptyList())
        ).join();

    }

    private List<CompletableFuture<Optional<God>>> checkGodsAndCount(final List<String> gods, final String mahabharata) {

        return gods.stream()
                .map(god -> indianGodService.isValidIndianGod(god)
                        .thenCompose(valid -> checkValidAndCount(god, valid, mahabharata)))
                .collect(toList());

    }

    private CompletableFuture<Optional<God>> checkValidAndCount(final String god,
                                                                final boolean valid,
                                                                final String mahabharata) {

        return valid ? countAppearances(god, mahabharata) : CompletableFuture.completedFuture(Optional.empty());

    }

    private CompletableFuture<Optional<God>> countAppearances(final String god, final String mahabharata) {
        return CompletableFuture.supplyAsync(() -> {
            LOG.info("Calculating the appearances for god: {}", god);
            return Optional.of(new God(god, StringUtils.countMatches(mahabharata, god)));
        });
    }

}
