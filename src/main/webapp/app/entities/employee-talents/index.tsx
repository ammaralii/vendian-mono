import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import EmployeeTalents from './employee-talents';
import EmployeeTalentsDetail from './employee-talents-detail';
import EmployeeTalentsUpdate from './employee-talents-update';
import EmployeeTalentsDeleteDialog from './employee-talents-delete-dialog';

const EmployeeTalentsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<EmployeeTalents />} />
    <Route path="new" element={<EmployeeTalentsUpdate />} />
    <Route path=":id">
      <Route index element={<EmployeeTalentsDetail />} />
      <Route path="edit" element={<EmployeeTalentsUpdate />} />
      <Route path="delete" element={<EmployeeTalentsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default EmployeeTalentsRoutes;
