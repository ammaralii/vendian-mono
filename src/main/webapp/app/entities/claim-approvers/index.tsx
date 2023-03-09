import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ClaimApprovers from './claim-approvers';
import ClaimApproversDetail from './claim-approvers-detail';
import ClaimApproversUpdate from './claim-approvers-update';
import ClaimApproversDeleteDialog from './claim-approvers-delete-dialog';

const ClaimApproversRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ClaimApprovers />} />
    <Route path="new" element={<ClaimApproversUpdate />} />
    <Route path=":id">
      <Route index element={<ClaimApproversDetail />} />
      <Route path="edit" element={<ClaimApproversUpdate />} />
      <Route path="delete" element={<ClaimApproversDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ClaimApproversRoutes;
