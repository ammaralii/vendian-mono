import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Approvers from './approvers';
import ApproversDetail from './approvers-detail';
import ApproversUpdate from './approvers-update';
import ApproversDeleteDialog from './approvers-delete-dialog';

const ApproversRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Approvers />} />
    <Route path="new" element={<ApproversUpdate />} />
    <Route path=":id">
      <Route index element={<ApproversDetail />} />
      <Route path="edit" element={<ApproversUpdate />} />
      <Route path="delete" element={<ApproversDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ApproversRoutes;
