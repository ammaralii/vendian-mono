import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import EmployeeRoles from './employee-roles';
import EmployeeRolesDetail from './employee-roles-detail';
import EmployeeRolesUpdate from './employee-roles-update';
import EmployeeRolesDeleteDialog from './employee-roles-delete-dialog';

const EmployeeRolesRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<EmployeeRoles />} />
    <Route path="new" element={<EmployeeRolesUpdate />} />
    <Route path=":id">
      <Route index element={<EmployeeRolesDetail />} />
      <Route path="edit" element={<EmployeeRolesUpdate />} />
      <Route path="delete" element={<EmployeeRolesDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default EmployeeRolesRoutes;
