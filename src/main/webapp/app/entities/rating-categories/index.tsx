import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import RatingCategories from './rating-categories';
import RatingCategoriesDetail from './rating-categories-detail';
import RatingCategoriesUpdate from './rating-categories-update';
import RatingCategoriesDeleteDialog from './rating-categories-delete-dialog';

const RatingCategoriesRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<RatingCategories />} />
    <Route path="new" element={<RatingCategoriesUpdate />} />
    <Route path=":id">
      <Route index element={<RatingCategoriesDetail />} />
      <Route path="edit" element={<RatingCategoriesUpdate />} />
      <Route path="delete" element={<RatingCategoriesDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default RatingCategoriesRoutes;
