import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import EmployeeBirthdays from './employee-birthdays';
import EmployeeBirthdaysDetail from './employee-birthdays-detail';
import EmployeeBirthdaysUpdate from './employee-birthdays-update';
import EmployeeBirthdaysDeleteDialog from './employee-birthdays-delete-dialog';

const EmployeeBirthdaysRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<EmployeeBirthdays />} />
    <Route path="new" element={<EmployeeBirthdaysUpdate />} />
    <Route path=":id">
      <Route index element={<EmployeeBirthdaysDetail />} />
      <Route path="edit" element={<EmployeeBirthdaysUpdate />} />
      <Route path="delete" element={<EmployeeBirthdaysDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default EmployeeBirthdaysRoutes;
