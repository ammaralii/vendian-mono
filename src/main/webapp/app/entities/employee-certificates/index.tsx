import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import EmployeeCertificates from './employee-certificates';
import EmployeeCertificatesDetail from './employee-certificates-detail';
import EmployeeCertificatesUpdate from './employee-certificates-update';
import EmployeeCertificatesDeleteDialog from './employee-certificates-delete-dialog';

const EmployeeCertificatesRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<EmployeeCertificates />} />
    <Route path="new" element={<EmployeeCertificatesUpdate />} />
    <Route path=":id">
      <Route index element={<EmployeeCertificatesDetail />} />
      <Route path="edit" element={<EmployeeCertificatesUpdate />} />
      <Route path="delete" element={<EmployeeCertificatesDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default EmployeeCertificatesRoutes;
