import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import LeaveStatuses from './leave-statuses';
import LeaveStatusesDetail from './leave-statuses-detail';
import LeaveStatusesUpdate from './leave-statuses-update';
import LeaveStatusesDeleteDialog from './leave-statuses-delete-dialog';

const LeaveStatusesRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<LeaveStatuses />} />
    <Route path="new" element={<LeaveStatusesUpdate />} />
    <Route path=":id">
      <Route index element={<LeaveStatusesDetail />} />
      <Route path="edit" element={<LeaveStatusesUpdate />} />
      <Route path="delete" element={<LeaveStatusesDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default LeaveStatusesRoutes;
