import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import UserRatingsRemarks from './user-ratings-remarks';
import UserRatingsRemarksDetail from './user-ratings-remarks-detail';
import UserRatingsRemarksUpdate from './user-ratings-remarks-update';
import UserRatingsRemarksDeleteDialog from './user-ratings-remarks-delete-dialog';

const UserRatingsRemarksRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<UserRatingsRemarks />} />
    <Route path="new" element={<UserRatingsRemarksUpdate />} />
    <Route path=":id">
      <Route index element={<UserRatingsRemarksDetail />} />
      <Route path="edit" element={<UserRatingsRemarksUpdate />} />
      <Route path="delete" element={<UserRatingsRemarksDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default UserRatingsRemarksRoutes;
