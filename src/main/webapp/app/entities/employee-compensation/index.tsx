import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import EmployeeCompensation from './employee-compensation';
import EmployeeCompensationDetail from './employee-compensation-detail';
import EmployeeCompensationUpdate from './employee-compensation-update';
import EmployeeCompensationDeleteDialog from './employee-compensation-delete-dialog';

const EmployeeCompensationRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<EmployeeCompensation />} />
    <Route path="new" element={<EmployeeCompensationUpdate />} />
    <Route path=":id">
      <Route index element={<EmployeeCompensationDetail />} />
      <Route path="edit" element={<EmployeeCompensationUpdate />} />
      <Route path="delete" element={<EmployeeCompensationDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default EmployeeCompensationRoutes;
