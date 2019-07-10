package com.example.energia.servicedesk.infrastructure.config;

import com.example.energia.servicedesk.ticket.Ticket;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class IdExposingRestConfigurerTest {

    @Mock
    private RepositoryRestConfiguration config;

    @Test
    public void configureRepositoryRestConfiguration_ExposesIdForTicket() {
        IdExposingRestConfigurer configurer = new IdExposingRestConfigurer();
        configurer.configureRepositoryRestConfiguration(config);
        verify(config).exposeIdsFor(eq(Ticket.class));
    }

}
