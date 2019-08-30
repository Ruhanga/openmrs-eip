package org.openmrs.sync.component.camel.extract;

import org.apache.camel.Component;
import org.apache.camel.Consumer;
import org.apache.camel.Producer;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openmrs.sync.component.camel.extract.fetchmodels.FetchModelsRuleEngine;
import org.openmrs.sync.component.service.TableToSyncEnum;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class OpenMrsExtractEndpointTest {

    @Mock
    private Component component;

    @Mock
    private FetchModelsRuleEngine ruleEngine;

    private OpenMrsExtractEndpoint endpoint;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        endpoint = new OpenMrsExtractEndpoint("testEndPoint", component, ruleEngine, TableToSyncEnum.PERSON);
    }

    @Test
    public void createProducer() {
        // Given

        // When
        Producer producer = endpoint.createProducer();

        // Then
        assertTrue(producer instanceof OpenMrsExtractProducer);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void createConsumer() {
        // Given

        // When
        Consumer consumer = endpoint.createConsumer(exchange -> {});

        // Then
        // Exception
    }

    @Test
    public void isSingleton() {
        assertTrue(endpoint.isSingleton());
    }

    @Test
    public void getLastSyncDate() {
        // Given
        LocalDateTime date = LocalDateTime.now();

        // When
        endpoint.setLastSyncDate(date);

        // Then
        assertEquals(date, endpoint.getLastSyncDate());
    }
}