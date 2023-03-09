import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import RaterAttributeMappings from './rater-attribute-mappings';
import RaterAttributeMappingsDetail from './rater-attribute-mappings-detail';
import RaterAttributeMappingsUpdate from './rater-attribute-mappings-update';
import RaterAttributeMappingsDeleteDialog from './rater-attribute-mappings-delete-dialog';

const RaterAttributeMappingsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<RaterAttributeMappings />} />
    <Route path="new" element={<RaterAttributeMappingsUpdate />} />
    <Route path=":id">
      <Route index element={<RaterAttributeMappingsDetail />} />
      <Route path="edit" element={<RaterAttributeMappingsUpdate />} />
      <Route path="delete" element={<RaterAttributeMappingsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default RaterAttributeMappingsRoutes;
