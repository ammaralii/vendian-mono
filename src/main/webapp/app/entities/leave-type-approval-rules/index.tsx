import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import LeaveTypeApprovalRules from './leave-type-approval-rules';
import LeaveTypeApprovalRulesDetail from './leave-type-approval-rules-detail';
import LeaveTypeApprovalRulesUpdate from './leave-type-approval-rules-update';
import LeaveTypeApprovalRulesDeleteDialog from './leave-type-approval-rules-delete-dialog';

const LeaveTypeApprovalRulesRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<LeaveTypeApprovalRules />} />
    <Route path="new" element={<LeaveTypeApprovalRulesUpdate />} />
    <Route path=":id">
      <Route index element={<LeaveTypeApprovalRulesDetail />} />
      <Route path="edit" element={<LeaveTypeApprovalRulesUpdate />} />
      <Route path="delete" element={<LeaveTypeApprovalRulesDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default LeaveTypeApprovalRulesRoutes;
