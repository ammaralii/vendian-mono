import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import AttributePermissions from './attribute-permissions';
import AttributePermissionsDetail from './attribute-permissions-detail';
import AttributePermissionsUpdate from './attribute-permissions-update';
import AttributePermissionsDeleteDialog from './attribute-permissions-delete-dialog';

const AttributePermissionsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<AttributePermissions />} />
    <Route path="new" element={<AttributePermissionsUpdate />} />
    <Route path=":id">
      <Route index element={<AttributePermissionsDetail />} />
      <Route path="edit" element={<AttributePermissionsUpdate />} />
      <Route path="delete" element={<AttributePermissionsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AttributePermissionsRoutes;
