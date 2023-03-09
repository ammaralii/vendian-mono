import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import RatingAttributeMappings from './rating-attribute-mappings';
import RatingAttributeMappingsDetail from './rating-attribute-mappings-detail';
import RatingAttributeMappingsUpdate from './rating-attribute-mappings-update';
import RatingAttributeMappingsDeleteDialog from './rating-attribute-mappings-delete-dialog';

const RatingAttributeMappingsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<RatingAttributeMappings />} />
    <Route path="new" element={<RatingAttributeMappingsUpdate />} />
    <Route path=":id">
      <Route index element={<RatingAttributeMappingsDetail />} />
      <Route path="edit" element={<RatingAttributeMappingsUpdate />} />
      <Route path="delete" element={<RatingAttributeMappingsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default RatingAttributeMappingsRoutes;
