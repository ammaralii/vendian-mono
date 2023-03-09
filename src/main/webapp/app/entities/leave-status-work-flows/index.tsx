import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import LeaveStatusWorkFlows from './leave-status-work-flows';
import LeaveStatusWorkFlowsDetail from './leave-status-work-flows-detail';
import LeaveStatusWorkFlowsUpdate from './leave-status-work-flows-update';
import LeaveStatusWorkFlowsDeleteDialog from './leave-status-work-flows-delete-dialog';

const LeaveStatusWorkFlowsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<LeaveStatusWorkFlows />} />
    <Route path="new" element={<LeaveStatusWorkFlowsUpdate />} />
    <Route path=":id">
      <Route index element={<LeaveStatusWorkFlowsDetail />} />
      <Route path="edit" element={<LeaveStatusWorkFlowsUpdate />} />
      <Route path="delete" element={<LeaveStatusWorkFlowsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default LeaveStatusWorkFlowsRoutes;
