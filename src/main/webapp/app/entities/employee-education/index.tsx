import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import EmployeeEducation from './employee-education';
import EmployeeEducationDetail from './employee-education-detail';
import EmployeeEducationUpdate from './employee-education-update';
import EmployeeEducationDeleteDialog from './employee-education-delete-dialog';

const EmployeeEducationRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<EmployeeEducation />} />
    <Route path="new" element={<EmployeeEducationUpdate />} />
    <Route path=":id">
      <Route index element={<EmployeeEducationDetail />} />
      <Route path="edit" element={<EmployeeEducationUpdate />} />
      <Route path="delete" element={<EmployeeEducationDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default EmployeeEducationRoutes;
