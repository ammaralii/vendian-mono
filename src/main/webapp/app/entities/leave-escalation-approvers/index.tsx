import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import LeaveEscalationApprovers from './leave-escalation-approvers';
import LeaveEscalationApproversDetail from './leave-escalation-approvers-detail';
import LeaveEscalationApproversUpdate from './leave-escalation-approvers-update';
import LeaveEscalationApproversDeleteDialog from './leave-escalation-approvers-delete-dialog';

const LeaveEscalationApproversRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<LeaveEscalationApprovers />} />
    <Route path="new" element={<LeaveEscalationApproversUpdate />} />
    <Route path=":id">
      <Route index element={<LeaveEscalationApproversDetail />} />
      <Route path="edit" element={<LeaveEscalationApproversUpdate />} />
      <Route path="delete" element={<LeaveEscalationApproversDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default LeaveEscalationApproversRoutes;
