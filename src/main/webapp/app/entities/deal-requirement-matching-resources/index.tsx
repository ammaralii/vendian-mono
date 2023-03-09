import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import DealRequirementMatchingResources from './deal-requirement-matching-resources';
import DealRequirementMatchingResourcesDetail from './deal-requirement-matching-resources-detail';
import DealRequirementMatchingResourcesUpdate from './deal-requirement-matching-resources-update';
import DealRequirementMatchingResourcesDeleteDialog from './deal-requirement-matching-resources-delete-dialog';

const DealRequirementMatchingResourcesRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<DealRequirementMatchingResources />} />
    <Route path="new" element={<DealRequirementMatchingResourcesUpdate />} />
    <Route path=":id">
      <Route index element={<DealRequirementMatchingResourcesDetail />} />
      <Route path="edit" element={<DealRequirementMatchingResourcesUpdate />} />
      <Route path="delete" element={<DealRequirementMatchingResourcesDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default DealRequirementMatchingResourcesRoutes;
