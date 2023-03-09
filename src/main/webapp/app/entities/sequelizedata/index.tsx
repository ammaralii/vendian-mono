import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Sequelizedata from './sequelizedata';
import SequelizedataDetail from './sequelizedata-detail';
import SequelizedataUpdate from './sequelizedata-update';
import SequelizedataDeleteDialog from './sequelizedata-delete-dialog';

const SequelizedataRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Sequelizedata />} />
    <Route path="new" element={<SequelizedataUpdate />} />
    <Route path=":id">
      <Route index element={<SequelizedataDetail />} />
      <Route path="edit" element={<SequelizedataUpdate />} />
      <Route path="delete" element={<SequelizedataDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SequelizedataRoutes;
