import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ClaimDetails from './claim-details';
import ClaimDetailsDetail from './claim-details-detail';
import ClaimDetailsUpdate from './claim-details-update';
import ClaimDetailsDeleteDialog from './claim-details-delete-dialog';

const ClaimDetailsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ClaimDetails />} />
    <Route path="new" element={<ClaimDetailsUpdate />} />
    <Route path=":id">
      <Route index element={<ClaimDetailsDetail />} />
      <Route path="edit" element={<ClaimDetailsUpdate />} />
      <Route path="delete" element={<ClaimDetailsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ClaimDetailsRoutes;
