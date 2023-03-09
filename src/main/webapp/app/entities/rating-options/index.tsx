import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import RatingOptions from './rating-options';
import RatingOptionsDetail from './rating-options-detail';
import RatingOptionsUpdate from './rating-options-update';
import RatingOptionsDeleteDialog from './rating-options-delete-dialog';

const RatingOptionsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<RatingOptions />} />
    <Route path="new" element={<RatingOptionsUpdate />} />
    <Route path=":id">
      <Route index element={<RatingOptionsDetail />} />
      <Route path="edit" element={<RatingOptionsUpdate />} />
      <Route path="delete" element={<RatingOptionsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default RatingOptionsRoutes;
