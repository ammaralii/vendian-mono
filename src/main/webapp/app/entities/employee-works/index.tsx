import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import EmployeeWorks from './employee-works';
import EmployeeWorksDetail from './employee-works-detail';
import EmployeeWorksUpdate from './employee-works-update';
import EmployeeWorksDeleteDialog from './employee-works-delete-dialog';

const EmployeeWorksRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<EmployeeWorks />} />
    <Route path="new" element={<EmployeeWorksUpdate />} />
    <Route path=":id">
      <Route index element={<EmployeeWorksDetail />} />
      <Route path="edit" element={<EmployeeWorksUpdate />} />
      <Route path="delete" element={<EmployeeWorksDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default EmployeeWorksRoutes;
