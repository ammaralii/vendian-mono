import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import RolePermissions from './role-permissions';
import RolePermissionsDetail from './role-permissions-detail';
import RolePermissionsUpdate from './role-permissions-update';
import RolePermissionsDeleteDialog from './role-permissions-delete-dialog';

const RolePermissionsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<RolePermissions />} />
    <Route path="new" element={<RolePermissionsUpdate />} />
    <Route path=":id">
      <Route index element={<RolePermissionsDetail />} />
      <Route path="edit" element={<RolePermissionsUpdate />} />
      <Route path="delete" element={<RolePermissionsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default RolePermissionsRoutes;
