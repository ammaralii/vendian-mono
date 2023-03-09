import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import DealEvents from './deal-events';
import DealEventsDetail from './deal-events-detail';
import DealEventsUpdate from './deal-events-update';
import DealEventsDeleteDialog from './deal-events-delete-dialog';

const DealEventsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<DealEvents />} />
    <Route path="new" element={<DealEventsUpdate />} />
    <Route path=":id">
      <Route index element={<DealEventsDetail />} />
      <Route path="edit" element={<DealEventsUpdate />} />
      <Route path="delete" element={<DealEventsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default DealEventsRoutes;
