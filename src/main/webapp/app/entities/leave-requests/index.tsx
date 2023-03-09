import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import LeaveRequests from './leave-requests';
import LeaveRequestsDetail from './leave-requests-detail';
import LeaveRequestsUpdate from './leave-requests-update';
import LeaveRequestsDeleteDialog from './leave-requests-delete-dialog';

const LeaveRequestsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<LeaveRequests />} />
    <Route path="new" element={<LeaveRequestsUpdate />} />
    <Route path=":id">
      <Route index element={<LeaveRequestsDetail />} />
      <Route path="edit" element={<LeaveRequestsUpdate />} />
      <Route path="delete" element={<LeaveRequestsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default LeaveRequestsRoutes;
