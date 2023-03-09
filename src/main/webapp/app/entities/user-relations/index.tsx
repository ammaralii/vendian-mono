import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import UserRelations from './user-relations';
import UserRelationsDetail from './user-relations-detail';
import UserRelationsUpdate from './user-relations-update';
import UserRelationsDeleteDialog from './user-relations-delete-dialog';

const UserRelationsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<UserRelations />} />
    <Route path="new" element={<UserRelationsUpdate />} />
    <Route path=":id">
      <Route index element={<UserRelationsDetail />} />
      <Route path="edit" element={<UserRelationsUpdate />} />
      <Route path="delete" element={<UserRelationsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default UserRelationsRoutes;
