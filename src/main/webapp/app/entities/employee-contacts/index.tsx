import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import EmployeeContacts from './employee-contacts';
import EmployeeContactsDetail from './employee-contacts-detail';
import EmployeeContactsUpdate from './employee-contacts-update';
import EmployeeContactsDeleteDialog from './employee-contacts-delete-dialog';

const EmployeeContactsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<EmployeeContacts />} />
    <Route path="new" element={<EmployeeContactsUpdate />} />
    <Route path=":id">
      <Route index element={<EmployeeContactsDetail />} />
      <Route path="edit" element={<EmployeeContactsUpdate />} />
      <Route path="delete" element={<EmployeeContactsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default EmployeeContactsRoutes;
