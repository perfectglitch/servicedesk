package com.example.energia.servicedesk.ticket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import com.jayway.jsonpath.TypeRef;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * Not really sure how to go about Unit testing with Spring Data REST without much
 * action going on in those enchanted classes.
 * Something to consider could be testing for entity rules with Spring Validators and
 * adding some error handling on client side as well.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
public class TicketITest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private TicketRepository repository;

    @Autowired
    private ObjectMapper mapper;

    private static final String ACTIVE_TICKETS_PATH = "/api/tickets/search/findActive";

    private TypeRef<List<Ticket>> jsonTypeRef;
    private Configuration jsonPathConfig;

    @Before
    public void setup() {
        // TypeRef used to map pageable response JSON to list of tickets
        jsonTypeRef = new TypeRef<List<Ticket>>() {
        };

        // Reusing ObjectMapper in Spring which ignores unknown fields by default.
        jsonPathConfig = Configuration.builder()
            .mappingProvider(new JacksonMappingProvider(mapper)).build();
    }

    @Test
    public void testSingleTicketGet() throws Exception {
        // given
        Ticket dbTicket = repository.save(ticketBuilder()
            .summary("test_summary")
            .build());

        // when
        MockHttpServletResponse response = mvc.perform(get("/api/tickets/1"))
            .andReturn()
            .getResponse();

        Ticket ticket = mapper.readValue(response.getContentAsByteArray(), Ticket.class);

        // then
        assertThat("Id must match", ticket.getId(), is(dbTicket.getId()));
        assertThat("Created date must match", ticket.getCreated(), is(dbTicket.getCreated()));
        assertThat("Summary must match", ticket.getSummary(), is("test_summary"));
        assertThat("Description must match", ticket.getDescription(), is("test_description"));
        assertThat("Email must match", ticket.getEmail(), is("test.email@example.com"));
        assertThat("Priority must match", ticket.getPriority(), is(1));
        assertThat("Status must match", ticket.getStatus(), is(1));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testPaginationAndSortForListQuery() throws Exception {
        // given
        List<Ticket> ticketsToSave = IntStream
            .rangeClosed(1, 5)
            .mapToObj(i -> ticketBuilder().priority(i).build())
            .collect(Collectors.toList());

        // when
        repository.saveAll(ticketsToSave);
        MockHttpServletResponse result = mvc.perform(get(ACTIVE_TICKETS_PATH)
            .param("size", "3")
            .param("page", "0")
            .param("sort", "priority,desc"))
            .andReturn().getResponse();
        List<Ticket> tickets = extractTicketsFromPage(result.getContentAsString());

        // then
        assertThat("Must be limited by page size", tickets, hasSize(3));

        assertThat("Must be sorted by priority DESC", tickets, contains(
            hasProperty("priority", is(5)),
            hasProperty("priority", is(4)),
            hasProperty("priority", is(3))
        ));

        tickets.forEach(ticket -> {
            assertThat("Summary must match", ticket.getSummary(), is("test_summary"));
            assertThat("Description must match", ticket.getDescription(), is("test_description"));
            assertThat("Email must match", ticket.getEmail(), is("test.email@example.com"));
            assertThat("Status must match", ticket.getStatus(), is(1));
        });
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testActiveTicketsSearchExcludesClosed() throws Exception {
        // given
        Ticket closed = ticketBuilder().summary("closed_ticket").status(3).build();
        Ticket open1 = ticketBuilder().summary("open_ticket_1").status(1).build();
        Ticket open2 = ticketBuilder().summary("open_ticket_2").status(2).build();
        repository.saveAll(Arrays.asList(closed, open1, open2));

        // when
        MvcResult result = mvc.perform(get(ACTIVE_TICKETS_PATH)).andReturn();
        List<Ticket> tickets = extractTicketsFromPage(result.getResponse().getContentAsString());

        // then
        assertThat(tickets, hasSize(2));
        assertThat(tickets, contains(
            hasProperty("status", is(1)),
            hasProperty("status", is(2))
        ));
    }

    private List<Ticket> extractTicketsFromPage(String pageJson) {
        ReadContext context = JsonPath.parse(pageJson, jsonPathConfig);
        return context.read("$._embedded.tickets", jsonTypeRef);
    }

    private Ticket.TicketBuilder ticketBuilder() {
        return Ticket.builder()
            .summary("test_summary")
            .description("test_description")
            .email("test.email@example.com")
            .priority(1)
            .status(1);
    }

}
