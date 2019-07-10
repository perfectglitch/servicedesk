import React from 'react';
import ReactDOM from 'react-dom';
import TicketForm from './TicketForm';
import { initTicket } from './../../util/ticket/Helper';

it('renders without crashing', () => {
  const div = document.createElement('div');
  ReactDOM.render(<TicketForm open={ true } ticket={ initTicket() } />, div);
  ReactDOM.unmountComponentAtNode(div);
});
