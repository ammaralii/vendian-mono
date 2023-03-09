import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import DealResourceEventLogs from './deal-resource-event-logs';
import DealResourceEventLogsDetail from './deal-resource-event-logs-detail';
import DealResourceEventLogsUpdate from './deal-resource-event-logs-update';
import DealResourceEventLogsDeleteDialog from './deal-resource-event-logs-delete-dialog';

const DealResourceEventLogsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<DealResourceEventLogs />} />
    <Route path="new" element={<DealResourceEventLogsUpdate />} />
    <Route path=":id">
      <Route index element={<DealResourceEventLogsDetail />} />
      <Route path="edit" element={<DealResourceEventLogsUpdate />} />
      <Route path="delete" element={<DealResourceEventLogsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default DealResourceEventLogsRoutes;
