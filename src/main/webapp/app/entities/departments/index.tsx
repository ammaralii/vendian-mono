import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Departments from './departments';
import DepartmentsDetail from './departments-detail';
import DepartmentsUpdate from './departments-update';
import DepartmentsDeleteDialog from './departments-delete-dialog';

const DepartmentsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Departments />} />
    <Route path="new" element={<DepartmentsUpdate />} />
    <Route path=":id">
      <Route index element={<DepartmentsDetail />} />
      <Route path="edit" element={<DepartmentsUpdate />} />
      <Route path="delete" element={<DepartmentsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default DepartmentsRoutes;
