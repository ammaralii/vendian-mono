import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import EmployeeComments from './employee-comments';
import EmployeeCommentsDetail from './employee-comments-detail';
import EmployeeCommentsUpdate from './employee-comments-update';
import EmployeeCommentsDeleteDialog from './employee-comments-delete-dialog';

const EmployeeCommentsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<EmployeeComments />} />
    <Route path="new" element={<EmployeeCommentsUpdate />} />
    <Route path=":id">
      <Route index element={<EmployeeCommentsDetail />} />
      <Route path="edit" element={<EmployeeCommentsUpdate />} />
      <Route path="delete" element={<EmployeeCommentsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default EmployeeCommentsRoutes;
