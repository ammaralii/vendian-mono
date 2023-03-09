import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ClaimRequests from './claim-requests';
import ClaimRequestsDetail from './claim-requests-detail';
import ClaimRequestsUpdate from './claim-requests-update';
import ClaimRequestsDeleteDialog from './claim-requests-delete-dialog';

const ClaimRequestsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ClaimRequests />} />
    <Route path="new" element={<ClaimRequestsUpdate />} />
    <Route path=":id">
      <Route index element={<ClaimRequestsDetail />} />
      <Route path="edit" element={<ClaimRequestsUpdate />} />
      <Route path="delete" element={<ClaimRequestsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ClaimRequestsRoutes;
