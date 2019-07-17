import React from 'react';
import { render, fireEvent, cleanup } from '@testing-library/react';

import TicketForm from './TicketForm';
import { initTicket } from './../../util/ticket/Helper';

afterEach(cleanup);

describe('TicketForm', () => {
    it('is edit form if ticket id set', () => {
        // given
        const ticket = {
            id: 1,
            summary: "summary1",
            description: "description1",
            email: "email@example.com",
            status: 1,
            priority: 1
        };
        const isOpen = true;

        // when
        const {queryByText, findByText} = render(
            <TicketForm open={ isOpen } ticket={ ticket } />,
        );

        // then
        expect(findByText("Edit Ticket")).toBeDefined();
        expect(queryByText("New Ticket")).toBeNull();
    });

    it('is create form if ticket id not set', () => {
        // given
        const ticket = {
            summary: "",
            description: "",
            email: "",
            status: 1,
            priority: 1
        };
        const isOpen = true;

        // when
        const { queryByText, findByText } = render(
            <TicketForm open={ isOpen } ticket={ ticket } />,
        );

        // then
        expect(findByText("New Ticket")).toBeDefined();
        expect(queryByText("Edit Ticket")).toBeNull();
    });

    it('delegates actions to handlers provided via props', () => {
        // given
        const ticket = {
            summary: "summary1",
            description: "description1",
            email: "email@example.com",
            status: 1,
            priority: 1
        };
        const isOpen = true;

        const saveHandler = jest.fn();
        const closeHandler = jest.fn();
        const changeHandler = jest.fn();

        // when
        const { queryByText, getByText, getByLabelText } = render(
            <TicketForm onTicketFormSave={ saveHandler }
                onTicketFormClose={ closeHandler }
                onTicketFormChange={ changeHandler }
                open={ isOpen } ticket={ ticket } />,
        );

        fireEvent.click(getByText("Save"));
        fireEvent.click(getByText("Close"));
        fireEvent.change(getByLabelText("Summary"), {target: {value: 'new summary'}});

        // then
        expect(saveHandler.mock.calls.length).toBe(1);
        expect(closeHandler.mock.calls.length).toBe(1);
        expect(changeHandler.mock.calls.length).toBe(1);
    });

});
