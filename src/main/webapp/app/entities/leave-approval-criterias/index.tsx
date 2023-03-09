import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import LeaveApprovalCriterias from './leave-approval-criterias';
import LeaveApprovalCriteriasDetail from './leave-approval-criterias-detail';
import LeaveApprovalCriteriasUpdate from './leave-approval-criterias-update';
import LeaveApprovalCriteriasDeleteDialog from './leave-approval-criterias-delete-dialog';

const LeaveApprovalCriteriasRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<LeaveApprovalCriterias />} />
    <Route path="new" element={<LeaveApprovalCriteriasUpdate />} />
    <Route path=":id">
      <Route index element={<LeaveApprovalCriteriasDetail />} />
      <Route path="edit" element={<LeaveApprovalCriteriasUpdate />} />
      <Route path="delete" element={<LeaveApprovalCriteriasDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default LeaveApprovalCriteriasRoutes;
