package org.leandroloureiro.mahabharatagods.logic;

import org.apache.commons.lang3.StringUtils;
import org.leandroloureiro.mahabharatagods.model.God;
import org.leandroloureiro.mahabharatagods.services.IndianGodService;
import org.leandroloureiro.mahabharatagods.services.IndianGodsService;
import org.leandroloureiro.mahabharatagods.services.MahabharataDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toMap;

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

        final var indianGods = getValidGods();
        final var mahabharataContent = mahabharataDataSource.getMahabharataBook();

        return indianGods.thenCombine(mahabharataContent, (gods, mahabharata) -> gods.stream()
                   .collect(toMap(Function.identity(), s -> countAppearances(s, mahabharata)))
                   .entrySet().stream()
                   .map( e ->  new God(e.getKey(), e.getValue().join()))
                   .collect(Collectors.toList()))
                .join()
                .stream()
                .sorted(Comparator.comparingLong(God::getHitCount).reversed())
                .limit(3)
                .collect(Collectors.toUnmodifiableList());

    }

    private CompletableFuture<List<String>> getValidGods() {

        return indianGodsService.getGodList()
                .thenApply(gods -> gods.map(l -> {

                    final var godsList = l.stream()
                            .collect(toMap(Function.identity(), indianGodService::isValidIndianGod));

                    return godsList.entrySet().stream()
                            .filter(e -> e.getValue().join())
                            .map(Map.Entry::getKey)
                            .collect(Collectors.toList());

                }).orElse(emptyList()));
    }

    private CompletableFuture<Integer> countAppearances(final String god, final String mahabharata) {
        return CompletableFuture.supplyAsync(() -> {
            LOG.info("Calculating the appearances for god: {}", god);
            return StringUtils.countMatches(mahabharata, god);
        });
    }

}
