package com.example.energia.servicedesk.ticket;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TicketRepository extends PagingAndSortingRepository<Ticket, Long> {

    @Query("select t from Ticket t where status != 3")
    Page<Ticket> findActive(Pageable pageable);

}
