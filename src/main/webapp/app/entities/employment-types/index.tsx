import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import EmploymentTypes from './employment-types';
import EmploymentTypesDetail from './employment-types-detail';
import EmploymentTypesUpdate from './employment-types-update';
import EmploymentTypesDeleteDialog from './employment-types-delete-dialog';

const EmploymentTypesRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<EmploymentTypes />} />
    <Route path="new" element={<EmploymentTypesUpdate />} />
    <Route path=":id">
      <Route index element={<EmploymentTypesDetail />} />
      <Route path="edit" element={<EmploymentTypesUpdate />} />
      <Route path="delete" element={<EmploymentTypesDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default EmploymentTypesRoutes;
