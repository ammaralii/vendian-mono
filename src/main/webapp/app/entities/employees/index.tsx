import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Employees from './employees';
import EmployeesDetail from './employees-detail';
import EmployeesUpdate from './employees-update';
import EmployeesDeleteDialog from './employees-delete-dialog';

const EmployeesRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Employees />} />
    <Route path="new" element={<EmployeesUpdate />} />
    <Route path=":id">
      <Route index element={<EmployeesDetail />} />
      <Route path="edit" element={<EmployeesUpdate />} />
      <Route path="delete" element={<EmployeesDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default EmployeesRoutes;
