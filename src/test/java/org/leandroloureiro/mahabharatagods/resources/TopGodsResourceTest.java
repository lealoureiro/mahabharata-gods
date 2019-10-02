package org.leandroloureiro.mahabharatagods.resources;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.leandroloureiro.mahabharatagods.logic.TopMahabharataGods;
import org.leandroloureiro.mahabharatagods.model.God;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TopGodsResourceTest {

    @Mock
    private TopMahabharataGods topMahabharataGods;

    @InjectMocks
    private TopGodsResource resource;

    @Test
    void testTopMahabharataGods() {

        final List<God> gods = Collections.singletonList(new God("SomeGod", 10));

        when(topMahabharataGods.getTopMahabharataGods()).thenReturn(gods);

        final ResponseEntity<List<God>> result = resource.topGods();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(gods, result.getBody());

        verify(topMahabharataGods).getTopMahabharataGods();

        verifyNoMoreInteractions(topMahabharataGods);

    }

}