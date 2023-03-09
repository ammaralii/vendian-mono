import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ClaimRequestViews from './claim-request-views';
import ClaimRequestViewsDetail from './claim-request-views-detail';
import ClaimRequestViewsUpdate from './claim-request-views-update';
import ClaimRequestViewsDeleteDialog from './claim-request-views-delete-dialog';

const ClaimRequestViewsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ClaimRequestViews />} />
    <Route path="new" element={<ClaimRequestViewsUpdate />} />
    <Route path=":id">
      <Route index element={<ClaimRequestViewsDetail />} />
      <Route path="edit" element={<ClaimRequestViewsUpdate />} />
      <Route path="delete" element={<ClaimRequestViewsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ClaimRequestViewsRoutes;
