import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ClaimStatus from './claim-status';
import ClaimStatusDetail from './claim-status-detail';
import ClaimStatusUpdate from './claim-status-update';
import ClaimStatusDeleteDialog from './claim-status-delete-dialog';

const ClaimStatusRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ClaimStatus />} />
    <Route path="new" element={<ClaimStatusUpdate />} />
    <Route path=":id">
      <Route index element={<ClaimStatusDetail />} />
      <Route path="edit" element={<ClaimStatusUpdate />} />
      <Route path="delete" element={<ClaimStatusDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ClaimStatusRoutes;
