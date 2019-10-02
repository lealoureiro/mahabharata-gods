package org.leandroloureiro.mahabharatagods.logic;

import org.leandroloureiro.mahabharatagods.model.God;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * {@inheritDoc}
 */
@Service
public class TopMahabharataGodsImpl implements TopMahabharataGods {

    /**
     * {@inheritDoc}
     */
    @Override
    public List<God> getTopMahabharataGods() {
        return Collections.emptyList();
    }


}
