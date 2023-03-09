import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import UserRatings from './user-ratings';
import UserRatingsDetail from './user-ratings-detail';
import UserRatingsUpdate from './user-ratings-update';
import UserRatingsDeleteDialog from './user-ratings-delete-dialog';

const UserRatingsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<UserRatings />} />
    <Route path="new" element={<UserRatingsUpdate />} />
    <Route path=":id">
      <Route index element={<UserRatingsDetail />} />
      <Route path="edit" element={<UserRatingsUpdate />} />
      <Route path="delete" element={<UserRatingsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default UserRatingsRoutes;
