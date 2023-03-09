import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import AuditLogs from './audit-logs';
import AuditLogsDetail from './audit-logs-detail';
import AuditLogsUpdate from './audit-logs-update';
import AuditLogsDeleteDialog from './audit-logs-delete-dialog';

const AuditLogsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<AuditLogs />} />
    <Route path="new" element={<AuditLogsUpdate />} />
    <Route path=":id">
      <Route index element={<AuditLogsDetail />} />
      <Route path="edit" element={<AuditLogsUpdate />} />
      <Route path="delete" element={<AuditLogsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AuditLogsRoutes;
