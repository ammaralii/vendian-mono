import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import LeaveRequestsOlds from './leave-requests-olds';
import LeaveRequestsOldsDetail from './leave-requests-olds-detail';
import LeaveRequestsOldsUpdate from './leave-requests-olds-update';
import LeaveRequestsOldsDeleteDialog from './leave-requests-olds-delete-dialog';

const LeaveRequestsOldsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<LeaveRequestsOlds />} />
    <Route path="new" element={<LeaveRequestsOldsUpdate />} />
    <Route path=":id">
      <Route index element={<LeaveRequestsOldsDetail />} />
      <Route path="edit" element={<LeaveRequestsOldsUpdate />} />
      <Route path="delete" element={<LeaveRequestsOldsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default LeaveRequestsOldsRoutes;
