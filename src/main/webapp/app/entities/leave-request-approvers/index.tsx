import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import LeaveRequestApprovers from './leave-request-approvers';
import LeaveRequestApproversDetail from './leave-request-approvers-detail';
import LeaveRequestApproversUpdate from './leave-request-approvers-update';
import LeaveRequestApproversDeleteDialog from './leave-request-approvers-delete-dialog';

const LeaveRequestApproversRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<LeaveRequestApprovers />} />
    <Route path="new" element={<LeaveRequestApproversUpdate />} />
    <Route path=":id">
      <Route index element={<LeaveRequestApproversDetail />} />
      <Route path="edit" element={<LeaveRequestApproversUpdate />} />
      <Route path="delete" element={<LeaveRequestApproversDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default LeaveRequestApproversRoutes;
