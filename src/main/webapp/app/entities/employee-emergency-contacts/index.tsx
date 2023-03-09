import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import EmployeeEmergencyContacts from './employee-emergency-contacts';
import EmployeeEmergencyContactsDetail from './employee-emergency-contacts-detail';
import EmployeeEmergencyContactsUpdate from './employee-emergency-contacts-update';
import EmployeeEmergencyContactsDeleteDialog from './employee-emergency-contacts-delete-dialog';

const EmployeeEmergencyContactsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<EmployeeEmergencyContacts />} />
    <Route path="new" element={<EmployeeEmergencyContactsUpdate />} />
    <Route path=":id">
      <Route index element={<EmployeeEmergencyContactsDetail />} />
      <Route path="edit" element={<EmployeeEmergencyContactsUpdate />} />
      <Route path="delete" element={<EmployeeEmergencyContactsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default EmployeeEmergencyContactsRoutes;
