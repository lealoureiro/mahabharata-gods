package org.leandroloureiro.mahabharatagods.services;

import java.util.concurrent.CompletableFuture;

/**
 * Logic to check the validity of an Indian God
 */
public interface IndianGodService {


    /**
     * Check if Indian god is valid
     *
     * @param indianGod the name of the god
     * @return true if god is valid and false is god is not existent
     */
    CompletableFuture<Boolean> isValidIndianGod(String indianGod);

}
