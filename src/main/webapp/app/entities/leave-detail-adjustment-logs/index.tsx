import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import LeaveDetailAdjustmentLogs from './leave-detail-adjustment-logs';
import LeaveDetailAdjustmentLogsDetail from './leave-detail-adjustment-logs-detail';
import LeaveDetailAdjustmentLogsUpdate from './leave-detail-adjustment-logs-update';
import LeaveDetailAdjustmentLogsDeleteDialog from './leave-detail-adjustment-logs-delete-dialog';

const LeaveDetailAdjustmentLogsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<LeaveDetailAdjustmentLogs />} />
    <Route path="new" element={<LeaveDetailAdjustmentLogsUpdate />} />
    <Route path=":id">
      <Route index element={<LeaveDetailAdjustmentLogsDetail />} />
      <Route path="edit" element={<LeaveDetailAdjustmentLogsUpdate />} />
      <Route path="delete" element={<LeaveDetailAdjustmentLogsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default LeaveDetailAdjustmentLogsRoutes;
