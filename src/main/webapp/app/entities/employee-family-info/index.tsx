import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import EmployeeFamilyInfo from './employee-family-info';
import EmployeeFamilyInfoDetail from './employee-family-info-detail';
import EmployeeFamilyInfoUpdate from './employee-family-info-update';
import EmployeeFamilyInfoDeleteDialog from './employee-family-info-delete-dialog';

const EmployeeFamilyInfoRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<EmployeeFamilyInfo />} />
    <Route path="new" element={<EmployeeFamilyInfoUpdate />} />
    <Route path=":id">
      <Route index element={<EmployeeFamilyInfoDetail />} />
      <Route path="edit" element={<EmployeeFamilyInfoUpdate />} />
      <Route path="delete" element={<EmployeeFamilyInfoDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default EmployeeFamilyInfoRoutes;
