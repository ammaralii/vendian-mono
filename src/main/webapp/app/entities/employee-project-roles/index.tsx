import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import EmployeeProjectRoles from './employee-project-roles';
import EmployeeProjectRolesDetail from './employee-project-roles-detail';
import EmployeeProjectRolesUpdate from './employee-project-roles-update';
import EmployeeProjectRolesDeleteDialog from './employee-project-roles-delete-dialog';

const EmployeeProjectRolesRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<EmployeeProjectRoles />} />
    <Route path="new" element={<EmployeeProjectRolesUpdate />} />
    <Route path=":id">
      <Route index element={<EmployeeProjectRolesDetail />} />
      <Route path="edit" element={<EmployeeProjectRolesUpdate />} />
      <Route path="delete" element={<EmployeeProjectRolesDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default EmployeeProjectRolesRoutes;
