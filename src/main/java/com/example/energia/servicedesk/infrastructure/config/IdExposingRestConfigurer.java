package com.example.energia.servicedesk.infrastructure.config;

import com.example.energia.servicedesk.ticket.Ticket;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.stereotype.Component;

@Component
public class IdExposingRestConfigurer implements RepositoryRestConfigurer {

    /**
     * Spring Data REST repository does not expose entity ID values by default.
     * Since we're not using Hypermedia links in this example we need ID values
     * on frontend for editing records.
     */
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(Ticket.class);
    }

}
