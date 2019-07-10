import React from 'react';
import ReactDOM from 'react-dom';
import TicketDashboard from './TicketDashboard';

it('renders without crashing', () => {
  const div = document.createElement('div');
  ReactDOM.render(<TicketDashboard />, div);
  ReactDOM.unmountComponentAtNode(div);
});
