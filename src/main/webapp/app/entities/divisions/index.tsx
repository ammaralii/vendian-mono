import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Divisions from './divisions';
import DivisionsDetail from './divisions-detail';
import DivisionsUpdate from './divisions-update';
import DivisionsDeleteDialog from './divisions-delete-dialog';

const DivisionsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Divisions />} />
    <Route path="new" element={<DivisionsUpdate />} />
    <Route path=":id">
      <Route index element={<DivisionsDetail />} />
      <Route path="edit" element={<DivisionsUpdate />} />
      <Route path="delete" element={<DivisionsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default DivisionsRoutes;
