import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import EmployeeAddresses from './employee-addresses';
import EmployeeAddressesDetail from './employee-addresses-detail';
import EmployeeAddressesUpdate from './employee-addresses-update';
import EmployeeAddressesDeleteDialog from './employee-addresses-delete-dialog';

const EmployeeAddressesRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<EmployeeAddresses />} />
    <Route path="new" element={<EmployeeAddressesUpdate />} />
    <Route path=":id">
      <Route index element={<EmployeeAddressesDetail />} />
      <Route path="edit" element={<EmployeeAddressesUpdate />} />
      <Route path="delete" element={<EmployeeAddressesDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default EmployeeAddressesRoutes;
