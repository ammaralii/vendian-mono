import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import RatingAttributes from './rating-attributes';
import RatingAttributesDetail from './rating-attributes-detail';
import RatingAttributesUpdate from './rating-attributes-update';
import RatingAttributesDeleteDialog from './rating-attributes-delete-dialog';

const RatingAttributesRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<RatingAttributes />} />
    <Route path="new" element={<RatingAttributesUpdate />} />
    <Route path=":id">
      <Route index element={<RatingAttributesDetail />} />
      <Route path="edit" element={<RatingAttributesUpdate />} />
      <Route path="delete" element={<RatingAttributesDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default RatingAttributesRoutes;
