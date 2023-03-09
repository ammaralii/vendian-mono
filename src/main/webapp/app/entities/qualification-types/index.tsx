import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import QualificationTypes from './qualification-types';
import QualificationTypesDetail from './qualification-types-detail';
import QualificationTypesUpdate from './qualification-types-update';
import QualificationTypesDeleteDialog from './qualification-types-delete-dialog';

const QualificationTypesRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<QualificationTypes />} />
    <Route path="new" element={<QualificationTypesUpdate />} />
    <Route path=":id">
      <Route index element={<QualificationTypesDetail />} />
      <Route path="edit" element={<QualificationTypesUpdate />} />
      <Route path="delete" element={<QualificationTypesDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default QualificationTypesRoutes;
