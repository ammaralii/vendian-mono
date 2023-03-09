import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import EmployeeDocuments from './employee-documents';
import EmployeeDocumentsDetail from './employee-documents-detail';
import EmployeeDocumentsUpdate from './employee-documents-update';
import EmployeeDocumentsDeleteDialog from './employee-documents-delete-dialog';

const EmployeeDocumentsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<EmployeeDocuments />} />
    <Route path="new" element={<EmployeeDocumentsUpdate />} />
    <Route path=":id">
      <Route index element={<EmployeeDocumentsDetail />} />
      <Route path="edit" element={<EmployeeDocumentsUpdate />} />
      <Route path="delete" element={<EmployeeDocumentsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default EmployeeDocumentsRoutes;
