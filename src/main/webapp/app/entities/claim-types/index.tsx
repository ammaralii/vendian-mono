import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ClaimTypes from './claim-types';
import ClaimTypesDetail from './claim-types-detail';
import ClaimTypesUpdate from './claim-types-update';
import ClaimTypesDeleteDialog from './claim-types-delete-dialog';

const ClaimTypesRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ClaimTypes />} />
    <Route path="new" element={<ClaimTypesUpdate />} />
    <Route path=":id">
      <Route index element={<ClaimTypesDetail />} />
      <Route path="edit" element={<ClaimTypesUpdate />} />
      <Route path="delete" element={<ClaimTypesDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ClaimTypesRoutes;
