import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import NotificationEvents from './notification-events';
import NotificationEventsDetail from './notification-events-detail';
import NotificationEventsUpdate from './notification-events-update';
import NotificationEventsDeleteDialog from './notification-events-delete-dialog';

const NotificationEventsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<NotificationEvents />} />
    <Route path="new" element={<NotificationEventsUpdate />} />
    <Route path=":id">
      <Route index element={<NotificationEventsDetail />} />
      <Route path="edit" element={<NotificationEventsUpdate />} />
      <Route path="delete" element={<NotificationEventsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default NotificationEventsRoutes;
