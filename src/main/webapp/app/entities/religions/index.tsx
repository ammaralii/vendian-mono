import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Religions from './religions';
import ReligionsDetail from './religions-detail';
import ReligionsUpdate from './religions-update';
import ReligionsDeleteDialog from './religions-delete-dialog';

const ReligionsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Religions />} />
    <Route path="new" element={<ReligionsUpdate />} />
    <Route path=":id">
      <Route index element={<ReligionsDetail />} />
      <Route path="edit" element={<ReligionsUpdate />} />
      <Route path="delete" element={<ReligionsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ReligionsRoutes;
