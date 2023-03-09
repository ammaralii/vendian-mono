import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import LeaveRequestApproverFlows from './leave-request-approver-flows';
import LeaveRequestApproverFlowsDetail from './leave-request-approver-flows-detail';
import LeaveRequestApproverFlowsUpdate from './leave-request-approver-flows-update';
import LeaveRequestApproverFlowsDeleteDialog from './leave-request-approver-flows-delete-dialog';

const LeaveRequestApproverFlowsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<LeaveRequestApproverFlows />} />
    <Route path="new" element={<LeaveRequestApproverFlowsUpdate />} />
    <Route path=":id">
      <Route index element={<LeaveRequestApproverFlowsDetail />} />
      <Route path="edit" element={<LeaveRequestApproverFlowsUpdate />} />
      <Route path="delete" element={<LeaveRequestApproverFlowsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default LeaveRequestApproverFlowsRoutes;
