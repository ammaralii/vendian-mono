import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import RaterAttributes from './rater-attributes';
import RaterAttributesDetail from './rater-attributes-detail';
import RaterAttributesUpdate from './rater-attributes-update';
import RaterAttributesDeleteDialog from './rater-attributes-delete-dialog';

const RaterAttributesRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<RaterAttributes />} />
    <Route path="new" element={<RaterAttributesUpdate />} />
    <Route path=":id">
      <Route index element={<RaterAttributesDetail />} />
      <Route path="edit" element={<RaterAttributesUpdate />} />
      <Route path="delete" element={<RaterAttributesDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default RaterAttributesRoutes;
