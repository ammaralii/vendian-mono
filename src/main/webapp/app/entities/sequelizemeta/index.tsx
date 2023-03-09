import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Sequelizemeta from './sequelizemeta';
import SequelizemetaDetail from './sequelizemeta-detail';
import SequelizemetaUpdate from './sequelizemeta-update';
import SequelizemetaDeleteDialog from './sequelizemeta-delete-dialog';

const SequelizemetaRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Sequelizemeta />} />
    <Route path="new" element={<SequelizemetaUpdate />} />
    <Route path=":id">
      <Route index element={<SequelizemetaDetail />} />
      <Route path="edit" element={<SequelizemetaUpdate />} />
      <Route path="delete" element={<SequelizemetaDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SequelizemetaRoutes;
