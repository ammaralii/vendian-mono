import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Ratings from './ratings';
import RatingsDetail from './ratings-detail';
import RatingsUpdate from './ratings-update';
import RatingsDeleteDialog from './ratings-delete-dialog';

const RatingsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Ratings />} />
    <Route path="new" element={<RatingsUpdate />} />
    <Route path=":id">
      <Route index element={<RatingsDetail />} />
      <Route path="edit" element={<RatingsUpdate />} />
      <Route path="delete" element={<RatingsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default RatingsRoutes;
