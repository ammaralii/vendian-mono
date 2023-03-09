import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import MaritalStatuses from './marital-statuses';
import MaritalStatusesDetail from './marital-statuses-detail';
import MaritalStatusesUpdate from './marital-statuses-update';
import MaritalStatusesDeleteDialog from './marital-statuses-delete-dialog';

const MaritalStatusesRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<MaritalStatuses />} />
    <Route path="new" element={<MaritalStatusesUpdate />} />
    <Route path=":id">
      <Route index element={<MaritalStatusesDetail />} />
      <Route path="edit" element={<MaritalStatusesUpdate />} />
      <Route path="delete" element={<MaritalStatusesDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default MaritalStatusesRoutes;
