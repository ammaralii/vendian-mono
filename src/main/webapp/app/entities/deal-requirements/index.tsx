import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import DealRequirements from './deal-requirements';
import DealRequirementsDetail from './deal-requirements-detail';
import DealRequirementsUpdate from './deal-requirements-update';
import DealRequirementsDeleteDialog from './deal-requirements-delete-dialog';

const DealRequirementsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<DealRequirements />} />
    <Route path="new" element={<DealRequirementsUpdate />} />
    <Route path=":id">
      <Route index element={<DealRequirementsDetail />} />
      <Route path="edit" element={<DealRequirementsUpdate />} />
      <Route path="delete" element={<DealRequirementsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default DealRequirementsRoutes;
