package org.leandroloureiro.mahabharatagods.services;

import java.util.concurrent.CompletableFuture;

/**
 * Interface to get Mahabharata book
 */
public interface MahabharataDataSource {

    /**
     * Get Mahabharata book
     *
     * @return the content of Mahabharata book
     */
    CompletableFuture<String> getMahabharataBook();

}
