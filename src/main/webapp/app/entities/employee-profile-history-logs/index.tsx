import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import EmployeeProfileHistoryLogs from './employee-profile-history-logs';
import EmployeeProfileHistoryLogsDetail from './employee-profile-history-logs-detail';
import EmployeeProfileHistoryLogsUpdate from './employee-profile-history-logs-update';
import EmployeeProfileHistoryLogsDeleteDialog from './employee-profile-history-logs-delete-dialog';

const EmployeeProfileHistoryLogsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<EmployeeProfileHistoryLogs />} />
    <Route path="new" element={<EmployeeProfileHistoryLogsUpdate />} />
    <Route path=":id">
      <Route index element={<EmployeeProfileHistoryLogsDetail />} />
      <Route path="edit" element={<EmployeeProfileHistoryLogsUpdate />} />
      <Route path="delete" element={<EmployeeProfileHistoryLogsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default EmployeeProfileHistoryLogsRoutes;
