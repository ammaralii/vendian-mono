import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import EmployeeDetails from './employee-details';
import EmployeeDetailsDetail from './employee-details-detail';
import EmployeeDetailsUpdate from './employee-details-update';
import EmployeeDetailsDeleteDialog from './employee-details-delete-dialog';

const EmployeeDetailsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<EmployeeDetails />} />
    <Route path="new" element={<EmployeeDetailsUpdate />} />
    <Route path=":id">
      <Route index element={<EmployeeDetailsDetail />} />
      <Route path="edit" element={<EmployeeDetailsUpdate />} />
      <Route path="delete" element={<EmployeeDetailsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default EmployeeDetailsRoutes;
