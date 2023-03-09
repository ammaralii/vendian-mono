import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import PcStatuses from './pc-statuses';
import PcStatusesDetail from './pc-statuses-detail';
import PcStatusesUpdate from './pc-statuses-update';
import PcStatusesDeleteDialog from './pc-statuses-delete-dialog';

const PcStatusesRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<PcStatuses />} />
    <Route path="new" element={<PcStatusesUpdate />} />
    <Route path=":id">
      <Route index element={<PcStatusesDetail />} />
      <Route path="edit" element={<PcStatusesUpdate />} />
      <Route path="delete" element={<PcStatusesDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PcStatusesRoutes;
