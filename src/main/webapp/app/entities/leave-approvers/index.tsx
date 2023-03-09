import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import LeaveApprovers from './leave-approvers';
import LeaveApproversDetail from './leave-approvers-detail';
import LeaveApproversUpdate from './leave-approvers-update';
import LeaveApproversDeleteDialog from './leave-approvers-delete-dialog';

const LeaveApproversRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<LeaveApprovers />} />
    <Route path="new" element={<LeaveApproversUpdate />} />
    <Route path=":id">
      <Route index element={<LeaveApproversDetail />} />
      <Route path="edit" element={<LeaveApproversUpdate />} />
      <Route path="delete" element={<LeaveApproversDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default LeaveApproversRoutes;
