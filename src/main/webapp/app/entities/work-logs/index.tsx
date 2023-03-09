import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import WorkLogs from './work-logs';
import WorkLogsDetail from './work-logs-detail';
import WorkLogsUpdate from './work-logs-update';
import WorkLogsDeleteDialog from './work-logs-delete-dialog';

const WorkLogsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<WorkLogs />} />
    <Route path="new" element={<WorkLogsUpdate />} />
    <Route path=":id">
      <Route index element={<WorkLogsDetail />} />
      <Route path="edit" element={<WorkLogsUpdate />} />
      <Route path="delete" element={<WorkLogsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default WorkLogsRoutes;
