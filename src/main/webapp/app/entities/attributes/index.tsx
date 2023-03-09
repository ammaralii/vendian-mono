import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Attributes from './attributes';
import AttributesDetail from './attributes-detail';
import AttributesUpdate from './attributes-update';
import AttributesDeleteDialog from './attributes-delete-dialog';

const AttributesRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Attributes />} />
    <Route path="new" element={<AttributesUpdate />} />
    <Route path=":id">
      <Route index element={<AttributesDetail />} />
      <Route path="edit" element={<AttributesUpdate />} />
      <Route path="delete" element={<AttributesDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AttributesRoutes;
