package org.leandroloureiro.mahabharatagods.resources;

import org.leandroloureiro.mahabharatagods.logic.TopMahabharataGods;
import org.leandroloureiro.mahabharatagods.model.God;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TopGodsResource {

    private static final Logger LOG = LoggerFactory.getLogger(TopGodsResource.class);

    private final TopMahabharataGods topMahabharataGods;

    @Autowired
    public TopGodsResource(final TopMahabharataGods topMahabharataGods) {
        this.topMahabharataGods = topMahabharataGods;
    }

    @RequestMapping(value = "/top-gods", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<God>> topGods() {

        LOG.info("Requesting top gods presented in Mahabharata.");

        final var gods = topMahabharataGods.getTopMahabharataGods();

        return ResponseEntity.ok().body(gods);

    }

}
