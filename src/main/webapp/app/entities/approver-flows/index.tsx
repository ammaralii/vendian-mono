import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ApproverFlows from './approver-flows';
import ApproverFlowsDetail from './approver-flows-detail';
import ApproverFlowsUpdate from './approver-flows-update';
import ApproverFlowsDeleteDialog from './approver-flows-delete-dialog';

const ApproverFlowsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ApproverFlows />} />
    <Route path="new" element={<ApproverFlowsUpdate />} />
    <Route path=":id">
      <Route index element={<ApproverFlowsDetail />} />
      <Route path="edit" element={<ApproverFlowsUpdate />} />
      <Route path="delete" element={<ApproverFlowsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ApproverFlowsRoutes;
