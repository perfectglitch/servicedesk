import React from 'react';
import { render, cleanup } from '@testing-library/react';

import TicketDashboard from './TicketDashboard';

const EMPTY_TICKETS_RESPONSE = {
   _embedded:{
       tickets: []
   },
   page: {
       totalElements: 0,
       number: 0
   }
};

afterEach(cleanup);

describe('TicketDashboard', () => {
    beforeEach(() => {
        fetch.resetMocks();
    });

    it('renders without crashing', async () => {
        fetch.mockResponse(JSON.stringify(EMPTY_TICKETS_RESPONSE));

        const { queryByText } = render(
            <TicketDashboard />,
        );

        expect(fetch.mock.calls.length).toEqual(1);
        expect(await queryByText("Service Desk Dashboard")).not.toBeNull();
    });
});
